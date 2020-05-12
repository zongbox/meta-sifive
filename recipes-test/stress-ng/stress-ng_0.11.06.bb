SUMMARY = "A tool to load and stress a computer system"
HOMEPAGE = "http://kernel.ubuntu.com/~cking/stress-ng/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "zlib libaio libbsd attr libcap libgcrypt keyutils lksctp-tools"

SRC_URI = "http://kernel.ubuntu.com/~cking/tarballs/${BPN}/${BP}.tar.xz \
           file://0001-Revert-Makefile-force-sync-after-build-in-case-reboo.patch \
           "

SRC_URI[md5sum] = "62ec3c3a6809b6c3cf7f73e9c6f37faf"

UPSTREAM_CHECK_URI ?= "http://kernel.ubuntu.com/~cking/tarballs/${BPN}/"
UPSTREAM_CHECK_REGEX ?= "(?P<pver>\d+(\.\d+)+)\.tar"

LINUX_TEST_DIR = "linux_test"
TARGET_TEST_DIR = "${LINUX_TEST_DIR}/${PN}"

CFLAGS += "-Wall -Wextra -DVERSION='"$(VERSION)"'"

do_install_append() {
    install -d ${D}/${TARGET_TEST_DIR}
    install -m 755 ${S}/stress-ng ${D}/${TARGET_TEST_DIR}
}

FILES_${PN} += " ${TARGET_TEST_DIR}"
