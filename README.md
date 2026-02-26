# Yocto Build for Raspberry Pi4

Aim: Rust program in RP4 based on Yocto

Folder Structure

```
.
├── apps
│   └── my-rust-library-app
├── bitbake-cookerdaemon.log
├── build
│   ├── bitbake-cookerdaemon.log
│   ├── cache
│   ├── conf
│   └── tmp
├── cache
├── common
│   ├── downloads
│   └── sstate-cache
├── docker
│   └── Dockerfile
├── layers
│   ├── meta-raspberrypi
│   ├── meta-rust-app
│   └── poky
└── README.md
```

### setting up Docker environment

Run the Dockerfile on the host machine to create the docker container.

docker build -t yocto-rust-builder ./docker

docker run -it --rm -v $(pwd):/work yocto-rust-builder


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


1. Yocto supports only edition = "2021" -> cargo.toml edited.
2. axum crate was not available to so edited .bb file 
    
    # We tell the recipe to look for a second file that will hold the crate list
    include my-rust-library-app-crates.inc

    touch my-rust-library-app-crates.inc

    Populate the .inc file
    ```
    bitbake -c update_crates my-rust-library-app 
    ```

This can be used only to trigger app build
    ```
    bitbake my-rust-library-app
    ```
    
3. How to solve the lock version difference due to rust version difference.

# Go to the app folder
```
cd /work/apps/my-rust-library-app
```

# Delete the incompatible lockfile
```
rm Cargo.lock 
rm -rf target
```

# Generate a fresh one using the container's Cargo 1.75
```
cargo generate-lockfile 
```

### IMPORTANT!!!!

This sets the home, generates the lock, and then the variable disappears.

Conflict arises because Cargo and Yocto use **CARGO_HOME** for two different, 
incompatible purposes during the build process.
```
CARGO_HOME=/work/apps/cargo_cache_safe cargo generate-lockfile
```

### different approch using cargo vendor

CARGO_HOME=/work/apps/cargo_cache_safe cargo vendor
```


Both DL_DIR and SSTATE_DIR are in a common folder outside build
local.conf is updated.

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






