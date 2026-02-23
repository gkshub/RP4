# Yocto Build for Raspberry Pi4

Aim: Rust program in RP4 based on Yocto

### Setting Up the Repo

This is created based on the version **scarthgap**

1. Poky 
    ```
    git submodule add -b scarthgap git://git.yoctoproject.org/poky layers/poky
    ```
2. Raspberry Pi Support
    ```
    git submodule add -b scarthgap git://git.yoctoproject.org/meta-raspberrypi layers/meta-raspberrypi
    ```
3. 

Run the build setup

`source oe-init-build-env ../../build`


ONLY in the beginning!!
DON'T RUN AGAIN
```
bitbake-layers create-layer ../layers/meta-rust-app
```