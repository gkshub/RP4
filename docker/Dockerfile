# Use Ubuntu 22.04 as the base for Yocto compatibility
FROM ubuntu:22.04

# 1. Prevent interactive prompts during install
ENV DEBIAN_FRONTEND=noninteractive

# 2. Install Yocto Essential Dependencies
RUN apt-get update && apt-get install -y \
    gawk wget git diffstat unzip texinfo gcc-multilib \
    build-essential chrpath socat cpio python3 python3-pip \
    python3-pexpect xz-utils debianutils iputils-ping \
    python3-git python3-jinja2 libegl1-mesa libsdl1.2-dev \
    pylint xterm bsdmainutils curl locales \
    file zstd lz4 liblz4-tool \
    && rm -rf /var/lib/apt/lists/*

# 3. Set up Locales (Yocto will crash without this)
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8

# 4. Install Rust 1.75.0 (To match your Yocto version)
# This ensures 'cargo update' always makes a Version 3 lockfile
ENV RUSTUP_HOME=/usr/local/rustup \
    CARGO_HOME=/usr/local/cargo \
    PATH=/usr/local/cargo/bin:$PATH
RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y --default-toolchain 1.75.0

# 5. Create a non-root user (Bitbake refuses to run as root)
RUN useradd -ms /bin/bash builder
USER builder
WORKDIR /work

# Start in a bash shell
CMD ["/bin/bash"]