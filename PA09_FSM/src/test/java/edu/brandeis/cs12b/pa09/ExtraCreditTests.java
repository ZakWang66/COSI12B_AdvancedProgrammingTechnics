package edu.brandeis.cs12b.pa09;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class ExtraCreditTests {
	
	// Note that simply passing this test doesn't mean your GraphViz is accurate.
	// It just means it created some image files, which you should then look at and check.
	@Test
	public void testGraphViz() throws IOException {
		BinaryFSM binaryFSM = new BinaryFSM();
		buildGraphVizPNG(binaryFSM.toGraphVizString(), "binaryFSM");
		
		PhoneFSM phoneFSM = new PhoneFSM();
		buildGraphVizPNG(phoneFSM.toGraphVizString(), "phoneFSM");
	}
	
	
	/**
	 * A helper method that uses a third-party "graphviz-java" library to build images
	 * from GraphViz strings. You don't need to modify this method!
	 * 
	 * @param gv
	 * @param filename
	 * @throws IOException
	 */
	private void buildGraphVizPNG(String gv, String filename) throws IOException {
		if(gv == null) fail("GraphViz not implemented");
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		System.out.println("Creating image from GraphViz...");
		
		MutableGraph g = Parser.read(gv);
		File outputPNG = new File(filename + ".png");
		Graphviz.fromGraph(g).render(Format.PNG).toFile(outputPNG);
		
		if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(outputPNG);
		System.out.println("Created image at " + outputPNG.getAbsolutePath() + " from source:");
		System.out.println(gv);
	}

}
