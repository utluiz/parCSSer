CSS Selector Parser [![Build Status](https://travis-ci.org/utluiz/parCSSer.svg?branch=master)](https://travis-ci.org/utluiz/parCSSer)
===================



A CSS Selector Parser for Java.

## Why

Add jQuery-like capabilities to virtually any library, mainly [Jericho Selector](https://github.com/utluiz/jericho-selector/), an extension I wrote to [Jericho HTML Parser](http://jericho.htmlparser.net).

## How 

CSS3 Selector parser implementaion based on [W3C Official Specification][1].

Just add the following dependency:

	<dependency>
		<groupId>br.com.starcode.parccser</groupId>
		<artifactId>parccser</artifactId>
		<version>1.1.1-RELEASE</version>
	</dependency>

## Status

- All main features implemented. 
- All important methods covered by tests, including failure scenarios.
- Lacks UTF-8 Characters Support.

## Roadmap

- Improve error handling with mor accurate information
- Maybe... parse the whole CSS files
- Maybe... Implement [SAC](http://www.w3.org/Style/CSS/SAC/)
- Any idea?

## Get in touch

Please, fell free to tell me if you have any issue, suggestion, contribution, or comment. ;)

Just go to [http://luizricardo.org](http://luizricardo.org/en/who-i-am/#contact).

  [1]: http://www.w3.org/TR/css3-selectors/