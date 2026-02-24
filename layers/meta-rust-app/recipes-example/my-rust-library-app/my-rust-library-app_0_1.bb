SUMMARY = "Small Rust Hello World for RPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# This tells BitBake to use the Cargo build system
inherit cargo
FILESEXTRAPATHS:prepend := "${THISDIR}:"
# Point to the local source directory
SRC_URI = "file://Cargo.toml \
           file://src/main.rs"

# For local files, we need to set the S (Source) directory
S = "${WORKDIR}"

# Since it's a simple local build, we don't need a checksum for a git repo
# but we do need to ensure the structure is correct for Cargo
do_configure:prepend() {
    mkdir -p ${S}/src
    cp ${WORKDIR}/main.rs ${S}/src/
}