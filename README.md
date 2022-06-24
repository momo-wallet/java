# MoMo Payment Java


### MoMo - Payment Platform

Example code to integrate MoMo E-Wallet as payment method
- Online Payment: Desktop, Mobile website
- Offline payment: POS, Static QR, Dynamic QR
- Mobile Payment: App to App, In MoMo Application  

### Requirements
- At least Java 8
- Maven 

### Installing

For the snapshot version, you can add the following dependency to your `POM.xml`:
```     
        <dependency>
            <groupId>io.github.momo-wallet</groupId>
            <artifactId>momopayment</artifactId>
            <version>1.0</version>
        </dependency>
```
Remember to specify your repository as following:
```
        <repository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </repository>
```

## Usage 

### Test project:
To understand and visualize how to use the library better, please consult our [test project](https://github.com/lnguyen99/test-momopayment)

### Setting Up MoMo Environment 
MoMo provides 2 environments for integration: development(```dev```) and production(```prod```). 
The model for environment is located at ```Environment```. You can use the function `selectEnv(String target, String process)` to choose the appropriate environment setup for the processes you are calling. 
 
Example configuration is provided in ```environment.properties``` in `resources` folder. By default, slf4j2 is used with Console and RollingFile Appenders. 
Please create your own configurations files for environment and log setup.  

### Integration 
The library provides functions to conduct transactions through the All-In-One (AIO) Payment Gateway (```com.mservice.allinone```) and all other Payment (```com.mservice.pay```) options (App-In-App, POS, Dynamic QR Code)

For each payment options, you can choose to either use the provided code in ```Processors``` folder to immediately use MoMo services or extend from the models located in `Models` folder. To have a better sense of how the processors work, we recommend uncommented and run the code in ```PayGate``` and ```NonAIOPay``` 

## Documention

https://developers.momo.vn

## Acknowledgments
### Security Aalgorithms
- [HMAC 256](https://en.wikipedia.org/wiki/HMAC)
- [RSA - Rivest–Shamir–Adleman](https://en.wikipedia.org/wiki/RSA_(cryptosystem))
- [AES - Advanced Encryption Standard](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard)

### More
- [IPN - Instant Payment Notification](https://developer.paypal.com/docs/classic/products/instant-payment-notification/)

- [JSON - JavaScript Object Notation](https://www.json.org/)

## Languages
- Java

## Versioning

```
Version 1.0-SNAPSHOT
``` 

## Authors

* **Khang Đoàn** - khang.doan@mservice . com . vn
* **Hải Nguyễn** - hai.nguyen@mservice . com . vn
* **Linh Nguyễn** - linh.nguyen7@mservice . com . vn
* **Sang Lê** - sang.le@mservice . com . vn

## License
(c) MoMo

## Support
If you have any issues when integrate MoMo API, please find out in [`F.A.Q`](https://developers.momo.vn/#/docs/aio/?id=faq) or [`Exception handling`](https://developers.momo.vn/#/docs/error_code) section in our [documention](https://developers.momo.vn)
