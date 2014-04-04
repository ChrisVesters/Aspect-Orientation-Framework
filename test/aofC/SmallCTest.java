package aofC;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import org.junit.Test;

// TODO: comments
public class SmallCTest {
	
	private String output = "test" + File.separatorChar + "output" + File.separatorChar + "SmallC" + File.separatorChar;
	private String source = "test" + File.separatorChar + "source" + File.separatorChar + "SmallC" + File.separatorChar;

	@After
	public void tearDown() throws Exception {
		File outFile = new File(output + File.separatorChar + "aspects.c");
		outFile.delete();
	}
	
	private String getContent(String fileName) throws IOException {
		assert (fileName != null);
		
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String content = new String();
		String line = br.readLine();
		while (line != null) {
			content += line + "\n";
			line = br.readLine();
		}
		
		return content;
	}
	
	private boolean compare(String content1, String content2) throws IOException {
		assert (content1 != null);
		assert (content2 != null);
	
		String[] lines = content1.split("\n");
		String content = new String();
		int brackets = 0;
		
		for (String line: lines) {
			if (line.endsWith(";") && (brackets == 0)) {
				content2 = content2.replace(line, "");
			} else if (line.endsWith(";")) {
				content += line + "\n";
			} else if (line.endsWith("{")) {
				content += line + "\n";
				brackets++;
			} else if (line.endsWith("}")) {
				content += line;
				brackets--;
				
				if (brackets == 0) {
					content2 = content2.replace(content, "");
					content = new String();
				} else {
					content += "\n";
				}
			} else if (!line.trim().equals("")){
				content2 = content2.replace(line, "");
			} else if (brackets != 0) {
				content += line + "\n";
			}
			
		}
		
		return content2.trim().equals("");
	}

	@Test
	public void NoAspectFile() {
		String[] args = {"-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "NoAspectFile.c";
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void NoSourceFile() {
		String[] args = {"-a", source + "BeforeAdvice.aof", "-oDir", output};
		File oFile = null;
		try {
			CWeaver.main(args);
			
			oFile = new File(output + File.separatorChar + "aspects.c");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertFalse(oFile.exists());
	}
	
	@Test
	public void NoMatchingAspects() {
		String[] args = {"-a", source + "NoMatchingAspects.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "NoMatchingAspects.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void CallAdvice() {
		String[] args = {"-a", source + "CallAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "CallAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ExecuteAdvice() {
		String[] args = {"-a", source + "ExecuteAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "ExecuteAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void BeforeAdvice() {
		String[] args = {"-a", source + "BeforeAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "BeforeAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void AfterAdvice() {
		String[] args = {"-a", source + "AfterAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "AfterAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void NoProceedAroundAdvice() {
		String[] args = {"-a", source + "NoProceedAroundAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "NoProceedAroundAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderBeforeAdvice() {
		String[] args = {"-a", source + "OrderBeforeAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OrderBeforeAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderAfterAdvice() {
		String[] args = {"-a", source + "OrderAfterAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OrderAfterAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderAroundAdvice() {
		String[] args = {"-a", source + "OrderAroundAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OrderAroundAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderBeforeAroundAdvice() {
		String[] args = {"-a", source + "OrderBeforeAroundAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OrderBeforeAroundAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderAfterAroundAdvice() {
		String[] args = {"-a", source + "OrderAfterAroundAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OrderAfterAroundAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void AroundVoidFunction() {
		String[] args = {"-a", source + "AroundVoidFunction.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "AroundVoidFunction.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void AroundReturnFunction() {
		String[] args = {"-a", source + "AroundReturnFunction.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "AroundReturnFunction.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void MultipleRulesNoMatches() {
		String[] args = {"-a", source + "MultipleRulesNoMatches.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "MultipleRulesNoMatches.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void MultipleRulesOneMatches() {
		String[] args = {"-a", source + "MultipleRulesOneMatches.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "MultipleRulesOneMatches.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void MultipleRulesAllMatches() {
		String[] args = {"-a", source + "MultipleRulesAllMatches.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "MultipleRulesAllMatches.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ArgumentAdvice() {
		String[] args = {"-a", source + "ArgumentAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "ArgumentAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void NoProceedArgument() {
		String[] args = {"-a", source + "NoProceedArgument.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "NoProceedArgument.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OriginalProceedArgument() {
		String[] args = {"-a", source + "OriginalProceedArgument.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "OriginalProceedArgument.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ModifiedProceedArgument() {
		String[] args = {"-a", source + "ModifiedProceedArgument.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "ModifiedProceedArgument.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardType() {
		String[] args = {"-a", source + "WildcardType.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "Wildcard.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardName() {
		String[] args = {"-a", source + "WildcardName.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "Wildcard.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardArgument() {
		String[] args = {"-a", source + "WildcardArgument.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "Wildcard.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void SetAdvice() {
		String[] args = {"-a", source + "SetAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "SetAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void GetAdvice() {
		String[] args = {"-a", source + "GetAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "GetAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ArgumentMemberAdvice() {
		String[] args = {"-a", source + "ArgumentMemberAdvice.aof", "-s", source + "source.c", "-oDir", output};
		try {
			CWeaver.main(args);
			String oFile = output + File.separatorChar + "aspects.c";
			String sFile = output + File.separatorChar + "ArgumentMemberAdvice.c";
			
			String oContent = getContent(oFile);
			String sContent = getContent(sFile);
			
			assertTrue(compare(oContent, sContent));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
