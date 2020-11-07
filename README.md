# OCL Complexity Plugin
## (USE plugin for OCL complexity calculation on UML diagrams)
This project was developed within the [Software Systems Engineering group](https://ciencia.iscte-iul.pt/centres/istar-iul/groups/sse) at the [ISTAR Research Center](https://ciencia.iscte-iul.pt/centres/istar-iul) at the [ISCTE-IUL university](https://www.iscte-iul.pt/) in Lisbon, Portugal.

## Introduction
The Unified Modeling Language (UML) [1] was created by the Object Management Group (OMG) [2], and had its first specification draft proposed in January 1997. It’s currently the standard language used in software development for specifying, visualizing, constructing, and documenting artifacts of software systems. However, UML models aren’t typically precise enough to express all relevant aspects of a specification. To fill the existing gaps on these models, a formal language was proposed. OCL (Object Constraint Language) [3] can be used to express constraints (class invariants, pre- and post- conditions, ...), which allow UML models to be more precise and unambiguous. Over the past years, several studies have been conducted to assess the benefits of using OCL alongside UML models [4, 5], which has been proven advantageous to modelers once they overcome OCL’s initial learning curve.

Several support tools have been developed to assist in model-driven development, including the analysis and design phases where modelers need to interpret and write OCL expressions. These tools have their specific characteristics and provide a variety of useful functionalities including syntactic analysis, connection with the UML model, and debugging [6]. To the best of our knowledge, none of these tools provides syntax highlighting in the UML diagram for manually introduced OCL expressions, which we believe that could soften the learning curve for this language by reducing the mental burden when reading, analyzing and writing expressions.

OCL Highlight, which was developed in Java, is a plugin for the USE tool [7] (from Bremen University) provides a new OCL evaluation dialog that offers syntax highlighting in the
UML model when users evaluate a given OCL expression. The last version of this plugin also includes a ‘Config’ button, where the user can configure different colors for the highlight, and an action button to evaluate the complexity of OCL expressions (using metrics defined by Reynoso et al [8]).

## Demo
Is available <a href="https://youtu.be/ZVBQ7O5BFi8">here</a> and it takes less than 5 minutes to see!

## Notes (version 1.1)
### Installation
Put the OclComplexity-1.1.jar file (<a href="OclComplexity-1.1.jar">Download Jar</a>) in your use-x.x.x/plugins folder.

Run USE. A button with a red marker will appear on the plugins section.

### Requirements
Install USE: get an instalable version from Sourceforge <a href="https://sourceforge.net/projects/useocl/">here</a>.

## How to use

### Calculate complexity
1. Repeat steps 1-3 from the previous section (class diagram view is not necessary).
2. Input the OCL expression on 'Enter OCL expression:' text box and click on 'Evaluate OCL Complexity'. Results are shown in the large text area at the bottom of the view.
5. Click on 'Clear' to reset the results, or simply input a new OCL expression.

Click on 'Help: OCL Complexity' to explore OCL metrics definitions.

## Bibliography
* [1] Object Management Group. What is UML: Unified Modeling Language, 2005. Available: http://www.uml.org/what-is-uml.html.
* [2] Object Management Group (OMG). OMG - Object Management
Group, 2017. Available: http://www.omg.org/.
* [3] Object Constraint Language, version 2.4. Tech. Rep. February, 2014.
* [4] Briand, L. C., Labiche, Y., Di Penta, M., and Yan-Bondoc, H. An experimental investigation of formality in UML-based development. IEEE Transactions on Software Engineering (2005).
* [5] Briand, L. C., Labiche, Y., Yan, H. D., and Di Penta, M. A controlled experiment on the impact of the object constraint language in UML-based maintenance. In IEEE International Conference on Software Maintenance, ICSM (2004), pp. 380–389.
* [6] Toval, A., Requena, V., and Fernández, J. L. Emerging OCL tools. Software and Systems Modeling 2, 4 (dec 2003), 248–261.
* [7] USE: The UML-based Specification Environment. Available: https:// sourceforge.net/p/useocl/wiki/.
* [8] Reynoso, L., Genero, M., and Piattini, M. Measuring Ocl Expressions: an Approach Based on Cognitive Techniques. In Metrics for Software Conceptual Models. Imperial College Press, Distributed by World Scientific Publishing Co., jan 2005, pp. 161–206.
