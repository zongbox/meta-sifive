SUMMARY = "Litmus Tests RISC-V"
DESCRIPTION = "Litmus tests for the RISC-V concurrency architecture. \
               We expect there are opam and ocaml in system already."
HOMEPAGE = "https://github.com/litmus-tests/litmus-tests-riscv.git"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://README.md;md5=be57643f14eb996d01a1f9d2db0b7036"

PV = "git${SRCPV}"

SRC_URI = "git://github.com/litmus-tests/litmus-tests-riscv.git"
SRCREV="3782f858fad38ece70f6c8f489b58db04c2243a6"

S = "${WORKDIR}/git"
LINUX_TEST_DIR = "linux_test"
TARGET_TEST_DIR = "${LINUX_TEST_DIR}/${PN}"

TARGET_CFLAGS += "--sysroot=${STAGING_DIR_TARGET} -D_GNU_SOURCE -DFORCE_AFFINITY -Wall -std=gnu99 -O2 -pthread"
EXTRA_OEMAKE = 'hw-tests CORES=8 GCC=riscv64-oe-linux-gcc GCCOPTS="${TARGET_CFLAGS}"'

do_configure() {
    opam init
    opam update
    opam install herdtools7
}

do_compile() {
    eval `opam config env`
    oe_runmake
}

do_install() {
    install -d ${D}/${TARGET_TEST_DIR}
    install ${S}/hw-tests/run.exe ${D}/${TARGET_TEST_DIR}
    install ${S}/hw-tests/run.sh  ${D}/${TARGET_TEST_DIR}
}

FILES_${PN} += " ${TARGET_TEST_DIR}"
