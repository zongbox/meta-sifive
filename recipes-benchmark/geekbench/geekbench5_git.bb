SUMMARY = "Geekbnech cross-platform benchmark"
DESCRIPTION = "Geekbench 5 is a cross-platform benchmark that measures your \
               system's performance with the press of a button."
HOMEPAGE = "https://www.geekbench.com/"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://README.md;md5=9c41eb76982396b449f5d536922b3183"

PV = "5+git${SRCPV}"

SRC_URI = "git://git@github.com/sifive/GeekBench5.git;protocol=ssh"
SRCREV="39556b417ed47238ed94bbc92f9d87f11f83eaf5"

S = "${WORKDIR}/git"
LINUX_TEST_DIR = "linux_test"
TARGET_TEST_DIR = "${LINUX_TEST_DIR}/${PN}"

TARGET_CFLAGS += "--sysroot=${STAGING_DIR_TARGET}"

do_compile() {
    env RISCV_PATH=${TARGET_PREFIX} XCFLAG='${TARGET_CFLAGS}' \
    python scons.py \
    BUILD_CUDA=OFF PRO=ON BUILD_LICENSING=OFF BUILD_CLANG=OFF BUILD_LLVM=OFF \
    ARCH=riscv64 PLATFORM=posix VERBOSE=1
}

do_install() {
    install -d ${D}/${TARGET_TEST_DIR}
    install ${S}/build.riscv64/ecdsatest         ${D}/${TARGET_TEST_DIR}
    install ${S}/build.riscv64/geekbench5        ${D}/${TARGET_TEST_DIR}
    install ${S}/build.riscv64/geekbench_riscv64 ${D}/${TARGET_TEST_DIR}
    install ${S}/build.riscv64/geekbench.plar    ${D}/${TARGET_TEST_DIR}
    install ${S}/build.riscv64/test.plar         ${D}/${TARGET_TEST_DIR}
}

FILES_${PN} += " ${TARGET_TEST_DIR}"
