## TunaSushi

[![Release Version](https://img.shields.io/github/v/release/TunaSashimi/TunaSushi.svg)](https://github.com/TunaSashimi/TunaSushi/releases)
[![Code Size](https://img.shields.io/github/languages/code-size/TunaSashimi/TunaSushi)](https://github.com/TunaSashimi/TunaSushi)
[![Last Commit](https://img.shields.io/github/last-commit/TunaSashimi/TunaSushi)](https://github.com/TunaSashimi/TunaSushi/commits)
[![license](https://img.shields.io/github/license/TunaSashimi/TunaSushi)](https://github.com/TunaSashimi/TunaSushi/blob/master/LICENSE)

TunaSushi is a set of view libraries that help developers follow best practices, reduce boilerplate code, and write code that works consistently on Android versions and devices.

## Getting started

Step 1. Add the JitPack repository to your build file

To get a Git project into your build:
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```  
Step 2. Add the dependency
  
```gradle
  	dependencies {
	        implementation 'com.github.TunaSashimi:TunaSushi:1.0.05'
	}
```
	
## Known Issues

If the attributes defined in the TunaSushi library and the attributes defined in other libraries have the same name and different types, compilation errors will occur.The solution is to define an attribute with the same name in the attr of the main project, but the type contains both.

For example, The content attribute in TView in the TunaSushi library is string, and the content attribute in the constraintlayout library is reference.

When the types of attributes with the same name are inconsistent, you can configure a single content in the project, and the attribute is the union of the two.such as below.

```xml
	<attr name="content" format="reference|string" />
```

## License
TunaSushi is under the MIT license. See the [LICENSE](https://github.com/TunaSashimi/TunaSushi/blob/master/LICENSE) file for details.
	
## Demo Example

![WechatIMG1330](https://user-images.githubusercontent.com/8152969/123502113-4bbe8d00-d67c-11eb-9404-3fa25136ef2a.jpeg)
![WechatIMG1331](https://user-images.githubusercontent.com/8152969/123502117-51b46e00-d67c-11eb-8f76-1f7e19a164d1.jpeg)
![WechatIMG1332](https://user-images.githubusercontent.com/8152969/123502120-56792200-d67c-11eb-848f-4a6e96a26f57.jpeg)
![WechatIMG1333](https://user-images.githubusercontent.com/8152969/123502122-5c6f0300-d67c-11eb-8973-b9f8a2920511.jpeg)
![WechatIMG1334](https://user-images.githubusercontent.com/8152969/123502126-6264e400-d67c-11eb-8f44-13c546ed219e.jpeg)
![WechatIMG1335](https://user-images.githubusercontent.com/8152969/123502128-685ac500-d67c-11eb-9484-a027efe366b6.jpeg)
![WechatIMG1336](https://user-images.githubusercontent.com/8152969/123502131-6e50a600-d67c-11eb-91b1-fc987c815496.jpeg)
