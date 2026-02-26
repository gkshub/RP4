SUMMARY = "Small Rust Hello World for RPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo externalsrc

# Point to your source
EXTERNALSRC = "${THISDIR}/../../../../apps/my-rust-library-app"
S = "${EXTERNALSRC}"

# Prevent the symlink error you just saw
EXTERNALSRC_SYMLINKS = ""

# Keep build artifacts out of your source tree
EXTERNALSRC_BUILD = "${WORKDIR}/build"

# This solves the lock version difference due to rust version difference.

do_configure:prepend() {
    # 1. Create the .cargo directory in your source tree
    mkdir -p ${S}/.cargo

    # 2. Write the config.toml file
    cat << EOF > ${S}/.cargo/config.toml
[source.crates-io]
replace-with = "vendored-sources"

[source."git+https://github.com/rust-lang/cargo?tag=home-0.5.9"]
git = "https://github.com/rust-lang/cargo"
tag = "home-0.5.9"
replace-with = "vendored-sources"

[source."git+https://github.com/rust-random/getrandom?tag=v0.2.15"]
git = "https://github.com/rust-random/getrandom"
tag = "v0.2.15"
replace-with = "vendored-sources"
EOF
}