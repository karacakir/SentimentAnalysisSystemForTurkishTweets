package bitirme;
import opennlp.tools.ml.maxent.*;
import opennlp.tools.ml.maxent.io.GISModelReader;
import opennlp.tools.ml.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.ml.model.OnePassDataIndexer;
import opennlp.tools.ml.model.PlainTextFileDataReader;
import sun.security.jca.GetInstance.Instance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import opennlp.*;
import opennlp.tools.ml.model.AbstractModel;
import opennlp.tools.ml.model.AbstractModelWriter;
import opennlp.tools.ml.model.DataIndexer;
import opennlp.tools.ml.model.DataReader;
import opennlp.tools.ml.model.FileEventStream;
public class MaxEnt {
	public void MaximumEnthropy() throws IOException{
		
		String trainingFileName = "combinedPositive.txt";
		String modelFileName = "trained-model.maxent.gz";

		// Training a model from data stored in a file.
		// The training file contains one training sample per line.
		// Outcome (result) is the first element on each line.
		// Example:
		// result=1 a=1 b=1
		// result=0 a=0 b=1
		// ...
		DataIndexer indexer = new OnePassDataIndexer( new FileEventStream(trainingFileName)); 
		MaxentModel trainedMaxentModel = GIS.trainModel(100, indexer); // 100 iterations

		// Storing the trained model into a file for later use (gzipped)
		File outFile = new File(modelFileName);
		AbstractModelWriter writer = new SuffixSensitiveGISModelWriter((AbstractModel) trainedMaxentModel, outFile);
		writer.persist();

		// Loading the gzipped model from a file
		FileInputStream inputStream = new FileInputStream(modelFileName);
		InputStream decodedInputStream = new GZIPInputStream(inputStream);
		DataReader modelReader = new PlainTextFileDataReader(decodedInputStream);
		MaxentModel loadedMaxentModel = new GISModelReader(modelReader).getModel();

		// Now predicting the outcome using the loaded model
		String[] context = {"a=1", "b=0"};
		double[] outcomeProbs = loadedMaxentModel.eval(context);
		String outcome = loadedMaxentModel.getBestOutcome(outcomeProbs);
		System.out.println(outcome);
		
	}
	public static MaxentModel trainModel(DataIndexer di, int iterations) {return null;    }
}
