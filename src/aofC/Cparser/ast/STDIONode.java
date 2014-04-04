/**
 * 
 * @file PrintfNode.java
 * @description This file contains the printf node of the AST.
 * @author Yentl Van Tendeloo
 * @date 7/4/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;


/**
 * 
 * This class represents a printf node in an AST.
 * 
 **/
public class STDIONode extends ASTNode {

	private static final long serialVersionUID = 6203673528904874122L;

	@Override
	public void toCode(){

		// Emulate a normal function call, but skip the ssp instruction,
		// as we have no idea how many arguments are passed.
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		///////////////                  PRINTF              /////////////////
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		
		// We can not have normal variables, so we have allocated the following
		// 'temporary' locations
		// 0 HEADER
		// 1 HEADER
		// 2 HEADER
		// 3 HEADER
		// 4 HEADER
		// 5 --> address of first character to be printed (TEMP0)
		// 6 --> location of next parameter to be printed (TEMP1)
		// 7 --> the required width (TEMP2)
		// 8 --> the content width (TEMP3)
		// 9 --> the first parameter
		// Note that 6, 7 and 8 are originally of the array descriptor, though we don't need
		// this content and to save space, we just overwrite this
		CodeGenerator.insertLabel("l_print");
		CodeGenerator.insertCode("sep 30");
		// Make the TEMP1 variable point to the first parameter
		CodeGenerator.insertCode("lda 0 9");
		CodeGenerator.insertCode("str a 0 6");
		
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 0");
		
		// Now do the actual function
		CodeGenerator.insertLabel("l_print_body");
		CodeGenerator.insertCode("lod a 0 5");
		// Fetch the actual character
		CodeGenerator.insertCode("ind c");
		// TOS is now the character to be printed
		
		// Do some checks for 'special' inputs
		// Copy the value, as our checks will pop it from the stack
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c '%'");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_print_width_init");
		
		// Check for end of string
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 0");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_print_end");
		
		// It seems that we are just reading a normal character, so just show it
		CodeGenerator.insertCode("out c");
		// Increment the return counter by 1
		CodeGenerator.insertCode("lod i 0 0");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 0");
		CodeGenerator.insertCode("ujp l_print_next");
		
		CodeGenerator.insertLabel("l_print_width_init");
		// We can drop the previous character now
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("sli a");
		
		// Clear the 7 and 8 location
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 7");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 8");
		
		CodeGenerator.insertLabel("l_print_width");
		// Parse the width if available
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 5");
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("ind c");
		// Scanned the next character
		// Check if it is a number or not
		CodeGenerator.insertCode("dpl c");
		// ASCII of / is just 1 below 0, to also include the 0 itself
		CodeGenerator.insertCode("ldc c '/'");
		CodeGenerator.insertCode("grt c");
		// Jump if the value is less then '0'
		CodeGenerator.insertCode("fjp l_print_type");
		CodeGenerator.insertCode("dpl c");
		// ASCII of : is just 1 above 9, to also include the 9 itself
		CodeGenerator.insertCode("ldc c ':'");
		CodeGenerator.insertCode("les c");
		// Jump if the value is greater then '9'
		CodeGenerator.insertCode("fjp l_print_type");
		// Now we are sure that it is '0' < char < '9'
		CodeGenerator.insertCode("conv c i");
		CodeGenerator.insertCode("ldc c '0'");
		CodeGenerator.insertCode("conv c i");
		CodeGenerator.insertCode("sub i");
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("ldc i 10");
		CodeGenerator.insertCode("mul i");
		CodeGenerator.insertCode("add i");
		CodeGenerator.insertCode("str i 0 7");
		CodeGenerator.insertCode("lod a 0 5");
		// Maybe there is another digit for the width, so go back
		CodeGenerator.insertCode("ujp l_print_width");
		
		CodeGenerator.insertLabel("l_print_type");
		// We will output _at least_ this number of characters
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'i'");
		CodeGenerator.insertCode("neq c");
		// Desired to output an integer
		CodeGenerator.insertCode("fjp l_print_integer_width");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'd'");
		CodeGenerator.insertCode("neq c");
		// Desired to output an integer
		CodeGenerator.insertCode("fjp l_print_integer_width");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'c'");
		CodeGenerator.insertCode("neq c");
		// Desired to output a character
		CodeGenerator.insertCode("fjp l_print_char_width");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'f'");
		CodeGenerator.insertCode("neq c");
		// Desired to output a float
		CodeGenerator.insertCode("fjp l_print_float_width");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 's'");
		CodeGenerator.insertCode("neq c");
		// Desired to output a string
		CodeGenerator.insertCode("fjp l_print_string_width");
		
		// Something went wrong, as we didn't match anything we support
		CodeGenerator.insertCode("retf");
		
		CodeGenerator.insertLabel("l_print_char_width");
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("sli a");
		CodeGenerator.insertCode("ind c");
		// The char to be printed is on TOS now
		
		// Set the width of the character to print, a character is always width 1, so this is easy
		CodeGenerator.insertCode("ldc i 1");
		CodeGenerator.insertCode("str i 0 8");
		
		// Now fetch the number of spaces to print
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("sub i");
		
		// Print the actual number of spaces
		CodeGenerator.insertLabel("l_print_c_TOS_width");
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("grt i");
		CodeGenerator.insertCode("fjp l_print_char_pop");
		CodeGenerator.insertCode("dec i 1");
		CodeGenerator.insertCode("ldc c ' '");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("ujp l_print_c_TOS_width");
		
		CodeGenerator.insertLabel("l_print_char_pop");
		// Now a number will be on top of stack, which we want to get rid of
		CodeGenerator.insertCode("sro i 0");
		CodeGenerator.insertCode("ujp l_print_char");
		
		// Actually print the char
		CodeGenerator.insertLabel("l_print_char");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("ujp l_print_next_param");
		
		CodeGenerator.insertLabel("l_print_string_width");		
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("sli a");
		CodeGenerator.insertCode("ind a");
		// The string to be printed is on TOS now
		
		// Now we must fill in the actual width
		// Copy the actual value, as we will be doing some operations
		// TOS: the address of the character to print
		CodeGenerator.insertLabel("l_print_s_width_find");
		// Loop over the float and keep dividing it by 10, while increasing a counter
		CodeGenerator.insertCode("dpl a");
		CodeGenerator.insertCode("ind c");
		CodeGenerator.insertCode("ldc c '\0'");
		CodeGenerator.insertCode("neq c");
		// If we jump, it means that the string is finished, so the width found
		CodeGenerator.insertCode("fjp l_print_string_calc");
		// Not yet complete, decrease the desired width by 1
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 8");
		// And advance the checker
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("ujp l_print_s_width_find");
		
		CodeGenerator.insertLabel("l_print_float_width");
		// Set the minimal starting width of a float (precision 6 + 2 chars before them)
		CodeGenerator.insertCode("ldc i 8");
		CodeGenerator.insertCode("str i 0 8");

		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("sli a");
		CodeGenerator.insertCode("ind r");
		// The float to be printed is on TOS now

		// Deal with negative floats that possibly bypass this
		CodeGenerator.insertCode("dpl r");
		CodeGenerator.insertCode("ldc r 0.0");
		CodeGenerator.insertCode("les r");
		// float > 0, so no need for this extra code
		CodeGenerator.insertCode("fjp l_print_f_width_find");
		// float < 0, so set the width to 1 higher automatically and negate the actual value
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 8");
		CodeGenerator.insertCode("neg r");
		
		// Now we must fill in the actual width
		// Copy the actual value, as we will be doing some operations
		// TOS: the float to print
		CodeGenerator.insertLabel("l_print_f_width_find");
		// Loop over the float and keep dividing it by 10, while increasing a counter
		CodeGenerator.insertCode("dpl r");
		CodeGenerator.insertCode("ldc r 10.0");
		CodeGenerator.insertCode("grt r");
		// If we jump, it means that the real is < 10, so the width is now exactly 8
		CodeGenerator.insertCode("fjp l_print_float_calc");
		// Not yet complete, decrease the desired width by 1
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 8");
		// And divide the float by 10
		CodeGenerator.insertCode("ldc r 10.0");
		CodeGenerator.insertCode("div r");
		CodeGenerator.insertCode("ujp l_print_f_width_find");
		
		CodeGenerator.insertLabel("l_print_string_calc");
		// TOS: the string to print
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("sub i");
		// Throw away the intermediate results
		CodeGenerator.insertCode("sli i");
		// TOS is now the number of spaces still required
		CodeGenerator.insertCode("ujp l_print_s_TOS_width");
		
		CodeGenerator.insertLabel("l_print_float_calc");
		// TOS: the float to print
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("sub i");
		// Throw away the division results
		// Save the number of spaces to prepend in 0 7
		CodeGenerator.insertCode("ujp l_print_f_TOS_width");
		
		CodeGenerator.insertLabel("l_print_integer_width");
		// Integer is at least 1 wide
		CodeGenerator.insertCode("ldc i 1");
		CodeGenerator.insertCode("str i 0 8");
		
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("sli a");
		CodeGenerator.insertCode("ind i");
		// The integer to be printed is on TOS now
		
		// Deal with negative integers that possibly bypass this
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("les i");
		// int > 0, so no need for this extra code
		CodeGenerator.insertCode("fjp l_print_i_width_find");
		// Int < 0, so set the width to 1 higher automatically and negate the actual value
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 8");
		
		CodeGenerator.insertCode("neg i");
		
		// Now we must fill in the actual width
		// TOS: the int to print
		CodeGenerator.insertLabel("l_print_i_width_find");
		// Loop over the integer and keep dividing it by 10, while increasing a counter
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 10");
		CodeGenerator.insertCode("geq i");
		// If we jump, it means that the int is < 10, so the width is now exactly 1
		CodeGenerator.insertCode("fjp l_print_i_width_calc");
		// Not yet complete, decrease the desired width by 1
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 8");
		// And divide the int by 10
		CodeGenerator.insertCode("ldc i 10");
		CodeGenerator.insertCode("div i");
		CodeGenerator.insertCode("ujp l_print_i_width_find");
		
		CodeGenerator.insertLabel("l_print_i_width_calc");
		
		// TOS: the integer to print
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("sub i");
		CodeGenerator.insertCode("sli i");
		
		// Save the number of spaces to prepend in 0 7
		CodeGenerator.insertCode("ujp l_print_i_TOS_width");
		
		// Now print the required number of spaces
		CodeGenerator.insertLabel("l_print_i_TOS_width");
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("grt i");
		CodeGenerator.insertCode("fjp l_print_integer_pop");
		CodeGenerator.insertCode("dec i 1");
		CodeGenerator.insertCode("ldc c ' '");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("ujp l_print_i_TOS_width");
		
		// Now print the required number of spaces
		CodeGenerator.insertLabel("l_print_s_TOS_width");
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("grt i");
		CodeGenerator.insertCode("fjp l_print_string_pop");
		CodeGenerator.insertCode("dec i 1");
		CodeGenerator.insertCode("ldc c ' '");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("ujp l_print_s_TOS_width");
		
		// Now print the required number of spaces
		CodeGenerator.insertLabel("l_print_f_TOS_width");
		CodeGenerator.insertCode("dpl i");
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("grt i");
		CodeGenerator.insertCode("fjp l_print_float_pop");
		CodeGenerator.insertCode("dec i 1");
		CodeGenerator.insertCode("ldc c ' '");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("ujp l_print_f_TOS_width");
		
		CodeGenerator.insertLabel("l_print_integer_pop");
		// Now a zero will be on top of stack, which we want to get rid of
		CodeGenerator.insertCode("sro i 0");
		
		// Actually print the integer
		CodeGenerator.insertLabel("l_print_integer");
		CodeGenerator.insertCode("out i");
		CodeGenerator.insertCode("ujp l_print_next_param");
		
		CodeGenerator.insertLabel("l_print_string_pop");
		// Now a zero will be on top of stack, which we want to get rid of
		CodeGenerator.insertCode("sro i 0");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		
		// Actually print the string
		CodeGenerator.insertLabel("l_print_string");
		CodeGenerator.insertCode("dpl a");
		CodeGenerator.insertCode("ind c");
		CodeGenerator.insertCode("ldc c '\0'");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_print_next_param_array");
		CodeGenerator.insertCode("dpl a");
		CodeGenerator.insertCode("ind c");
		CodeGenerator.insertCode("out c");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("ujp l_print_string");
		
		CodeGenerator.insertLabel("l_print_float_pop");
		// Now a zero will be on top of stack, which we want to get rid of
		CodeGenerator.insertCode("sro i 0");
		
		CodeGenerator.insertLabel("l_print_float");
		// Load 6 as this is the default precision on my system
		CodeGenerator.insertCode("ldc i 6");
		// We are allowed to use out r i for fixed precision (6 precise), since printf does the same
		CodeGenerator.insertCode("out r i");
		CodeGenerator.insertCode("ujp l_print_next_param");
		
		CodeGenerator.insertLabel("l_print_retwidth");
		// Load the number of characters that must AT LEAST be printed
		CodeGenerator.insertCode("lod i 0 7");
		// Load the number of characters that the content was
		CodeGenerator.insertCode("lod i 0 8");
		// Now two values are on the stack, the highest of them will be added to the return value
		CodeGenerator.insertCode("grt i");
		CodeGenerator.insertCode("fjp l_print_set_8");
		// It seems that 7 contained the highest value, so load this
		CodeGenerator.insertCode("lod i 0 7");
		CodeGenerator.insertCode("ujp l_print_set_done");
		CodeGenerator.insertLabel("l_print_set_8");
		// It seems that 8 contained the highest value, so load this
		CodeGenerator.insertCode("lod i 0 8");
		CodeGenerator.insertCode("ujp l_print_set_done");
		CodeGenerator.insertLabel("l_print_set_done");
		CodeGenerator.insertCode("lod i 0 0");
		CodeGenerator.insertCode("add i");
		// Add this to the current return value and continue
		CodeGenerator.insertCode("str i 0 0");
		CodeGenerator.insertCode("ujp l_print_next");
		
		// Help function: advance the parameter pointer by 5
		CodeGenerator.insertLabel("l_print_next_param_array");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("inc a 4");
		CodeGenerator.insertCode("str a 0 6");
		CodeGenerator.insertCode("ujp l_print_retwidth");
		
		// Help function: advance the parameter pointer
		CodeGenerator.insertLabel("l_print_next_param");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 6");
		CodeGenerator.insertCode("ujp l_print_retwidth");
		
		// Help function: go to the next character to be printed and call ourself
		CodeGenerator.insertLabel("l_print_next");
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 5");
		
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 8");
		// Reset the width field
		// This is done elsewhere
		CodeGenerator.insertCode("ujp l_print_body");
		
		// We are done
		CodeGenerator.insertLabel("l_print_end");
		CodeGenerator.insertCode("retf");
		
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		///////////////                  SCANF               /////////////////
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		
		
		// We can not have normal variables, so we have allocated the following
		// 'temporary' locations
		// We can not have normal variables, so we have allocated the following
		// 'temporary' locations
		// 0 HEADER
		// 1 HEADER
		// 2 HEADER
		// 3 HEADER
		// 4 HEADER
		// 5 --> address of first character to be printed (TEMP0)
		// 6 --> location of next parameter to be printed (TEMP1)
		// 7 --> reserved for future use (the width) (TEMP2)
		// 8 --> reserved for future use (TEMP3)
		// 9 --> the first parameter
		// Note that 6, 7 and 8 are originally of the array descriptor, though we don't need
		// this content and to save space, we just overwrite this
		CodeGenerator.insertLabel("l_scan");
		CodeGenerator.insertCode("sep 30");
		// Make the TEMP1 variable point to the first parameter
		CodeGenerator.insertCode("lda 0 9");
		CodeGenerator.insertCode("str a 0 6");
		
		// Set the return value to 0 in the beginning
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 0");
		
		// Now do the actual function
		CodeGenerator.insertLabel("l_scan_body");
		CodeGenerator.insertCode("lod a 0 5");
		// Fetch the actual character
		CodeGenerator.insertCode("ind c");
		// TOS is now the character to be scanned
		
		// Do some checks for 'special' inputs
		// Copy the value, as our checks will pop it from the stack
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c '%'");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_scan_var");
		
		// Check for end of string
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 0");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_scan_end");
		
		// It seems that we are just reading a normal character, so just show it
		CodeGenerator.insertCode("in c");
		CodeGenerator.insertCode("neq c");
		// Check if it is what we expected, otherwise we return immediately
		CodeGenerator.insertCode("ujp l_scan_next");
		CodeGenerator.insertCode("retf");
		
		CodeGenerator.insertLabel("l_scan_var");
		// Go to the next character
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 5");
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("ind c");
		
		// Now we should have the type on TOS
		// This means we are ready to progress to type parsing
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'i'");
		CodeGenerator.insertCode("neq c");
		// Desired to read an integer
		CodeGenerator.insertCode("fjp l_scan_integer");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'd'");
		CodeGenerator.insertCode("neq c");
		// Desired to read an integer
		CodeGenerator.insertCode("fjp l_scan_integer");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'c'");
		CodeGenerator.insertCode("neq c");
		// Desired to read a character
		CodeGenerator.insertCode("fjp l_scan_char");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 'f'");
		CodeGenerator.insertCode("neq c");
		// Desired to read a float
		CodeGenerator.insertCode("fjp l_scan_float");
		
		CodeGenerator.insertCode("dpl c");
		CodeGenerator.insertCode("ldc c 's'");
		CodeGenerator.insertCode("neq c");
		// Desired to read a float
		CodeGenerator.insertCode("fjp l_scan_string");
		
		// Something went wrong, as we didn't match anything we support
		CodeGenerator.insertCode("retf");
		
		CodeGenerator.insertLabel("l_scan_integer");
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("in i");
		CodeGenerator.insertCode("sto i");
		CodeGenerator.insertCode("ujp l_scan_next_param");
		
		CodeGenerator.insertLabel("l_scan_char");
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("in c");
		CodeGenerator.insertCode("sto c");
		CodeGenerator.insertCode("ujp l_scan_next_param");
		
		CodeGenerator.insertLabel("l_scan_string");
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("in c");
		CodeGenerator.insertCode("sto c");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("ind c");
		// Compare to the escape character
		CodeGenerator.insertCode("ldc c 27");
		CodeGenerator.insertCode("neq c");
		CodeGenerator.insertCode("fjp l_scan_next_param_array");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("sto a");
		// Read next character
		CodeGenerator.insertCode("ujp l_scan_string");
		
		CodeGenerator.insertLabel("l_scan_float");
		// Load the parameter on TOS
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("in r");
		CodeGenerator.insertCode("sto r");
		CodeGenerator.insertCode("ujp l_scan_next_param");
		
		// Help function: advance the parameter pointer
		CodeGenerator.insertLabel("l_scan_next_param_array");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("ind a");
		// As the escape character is not used when printing, we replace it with a null
		CodeGenerator.insertCode("ldc c '\0'");
		CodeGenerator.insertCode("sto c");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("inc a 4");
		CodeGenerator.insertCode("str a 0 6");
		CodeGenerator.insertCode("ujp l_scan_inc_ret");
		
		// Help function: advance the parameter pointer
		CodeGenerator.insertLabel("l_scan_next_param");
		CodeGenerator.insertCode("lod a 0 6");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 6");
		CodeGenerator.insertCode("ujp l_scan_inc_ret");
		
		CodeGenerator.insertLabel("l_scan_inc_ret");
		// Increment the return value by one as we matched a field
		CodeGenerator.insertCode("lod i 0 0");
		CodeGenerator.insertCode("inc i 1");
		CodeGenerator.insertCode("str i 0 0");
		CodeGenerator.insertCode("ujp l_scan_next");
		
		// Help function: go to the next character to be printed and call ourself
		CodeGenerator.insertLabel("l_scan_next");
		CodeGenerator.insertCode("lod a 0 5");
		CodeGenerator.insertCode("inc a 1");
		CodeGenerator.insertCode("str a 0 5");
		CodeGenerator.insertCode("ujp l_scan_body");
		
		// We are done
		CodeGenerator.insertLabel("l_scan_end");
		CodeGenerator.insertCode("retf");
	}

	@Override
	public int getEP() {
		return 0;
	}
}
