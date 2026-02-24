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
---

Run the build setup

```
source oe-init-build-env ../../build
```
---
ONLY in the beginning!! 
**DON'T RUN AGAIN** 

```
bitbake-layers create-layer ../layers/meta-rust-app
bitbake-layers add-layer ../layers/meta-rust-app/
```
---
Check if layers are properly added
```
bitbake-layers show-layers
```
---
---

### INITIAL Setup

**TEMPLATECONF** setup

```
mkdir -p layers/meta-rust-app/conf/templates/rpi-headless
```
Sample local.conf and bblayers.conf are copied from 
```
poky/meta-poky/conf/templates/default/local.conf.sample
```

1. machine and feature removals are put here: 

    `layers/meta-rust-app/conf/templates/rpi-headless/local.conf.sample`

2. the layers that must be active: 

    `layers/meta-rust-app/conf/templates/rpi-headless/bblayers.conf.sample`


### Configure Rust Project

layers/meta-rust-app/recipes-example/my-rust-library-app

bb file: layers/meta-rust-app/recipes-example/my-rust-library-app/my-rust-library-app_0_1.bb

To avoid the folder level error I have configured in bb file

```
FILESEXTRAPATHS:prepend := "${THISDIR}:"
```


The special project releated configuration are being done in the meta-rust-app layer so it should be informed to bitbake system before triggering build
```
export TEMPLATECONF=$(pwd)/layers/meta-rust-app/conf/templates/rpi-headless/ 
```
```
source layers/poky/oe-init-build-env build 
```
```
bitbake core-image-minimal 
```






