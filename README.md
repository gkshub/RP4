# Yocto Build for Raspberry Pi4

Aim: Rust program in RP4 based on Yocto

### Setting Up the Repo


1. Add Poky (The Core)
    ```
    git submodule add -b scarthgap git://git.yoctoproject.org/poky layers/poky
    ```
2. Add Raspberry Pi Support
    ```
    git submodule add -b scarthgap git://git.yoctoproject.org/meta-raspberrypi layers/meta-raspberrypi
    ```
