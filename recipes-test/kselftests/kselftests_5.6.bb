SUMMARY = "Linux Kernel Selftests"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI = "\
    https://www.kernel.org/pub/linux/kernel/v5.x/linux-${PV}.tar.xz \
    file://0001-selftests-lib-allow-to-override-CC-in-the-top-level-Makefile.patch \
"

SRC_URI[md5sum] = "7b9199ec5fa563ece9ed585ffb17798f"
SRC_URI[sha256sum] = "e342b04a2aa63808ea0ef1baab28fc520bd031ef8cf93d9ee4a31d4058fcb622"

S = "${WORKDIR}/linux-${PV}"
LINUX_TEST_DIR = "linux_test"
TARGET_TEST_DIR = "${LINUX_TEST_DIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libcap libcap-ng popt rsync-native util-linux pkgconfig-native \
"

inherit kernel-arch

EXTRA_OEMAKE += "V=1 -C ${S}/tools/testing/selftests INSTALL_PATH=${D}/${TARGET_TEST_DIR} CC="${CC}" LD="${LD}""

do_package_qa () {
  echo "work around for checking target package in scripts of kselftesting."
}

do_compile () {
	# Make sure to install the user space API used by some tests
	# but not properly declared as a build dependency
	${MAKE} -C ${S} headers_install
	oe_runmake
}

do_install () {
	oe_runmake install
	chown -R root:root ${D}
	# fixup run_kselftest.sh due to spurious lines starting by "make[1]:"
	sed -i '/^make/d' ${D}/${TARGET_TEST_DIR}/run_kselftest.sh
}

PACKAGE_BEFORE_PN = " \
	${PN}-android \
	${PN}-arm64 \
	${PN}-bpf \
	${PN}-breakpoints \
	${PN}-capabilities \
	${PN}-clone3 \
	${PN}-cpufreq \
	${PN}-cpu-hotplug \
	${PN}-drivers \
	${PN}-efivarfs \
	${PN}-exec \
	${PN}-filesystems \
	${PN}-firmware \
	${PN}-ftrace \
	${PN}-futex \
	${PN}-ipc \
	${PN}-ir \
	${PN}-kcmp \
	${PN}-lib \
	${PN}-livepatch \
	${PN}-lkdtm \
	${PN}-membarrier \
	${PN}-memory-hotplug \
	${PN}-mount \
	${PN}-mqueue \
	${PN}-net \
	${PN}-networking \
	${PN}-powerpc \
	${PN}-proc \
	${PN}-pstore \
	${PN}-ptrace \
	${PN}-rtc \
	${PN}-seccomp \
	${PN}-sigaltstack \
	${PN}-size \
	${PN}-sparc64 \
	${PN}-splice \
	${PN}-static-keys \
	${PN}-sync \
	${PN}-sysctl \
	${PN}-timers \
	${PN}-tmpfs \
	${PN}-tpm2 \
	${PN}-user \
	${PN}-vm \
	${PN}-x86 \
	${PN}-zram \
"

FILES_${PN}-android = "${TARGET_TEST_DIR}/android"
FILES_${PN}-arm64 = "${TARGET_TEST_DIR}/arm64"
FILES_${PN}-bpf = "${TARGET_TEST_DIR}/bpf"
FILES_${PN}-breakpoints = "${TARGET_TEST_DIR}/breakpoints"
FILES_${PN}-capabilities = "${TARGET_TEST_DIR}/capabilities"
FILES_${PN}-clone3 = "${TARGET_TEST_DIR}/clone3"
FILES_${PN}-cpufreq = "${TARGET_TEST_DIR}/cpufreq"
FILES_${PN}-cpu-hotplug = "${TARGET_TEST_DIR}/cpu-hotplug"
FILES_${PN}-drivers = "${TARGET_TEST_DIR}/drivers"
FILES_${PN}-efivarfs = "${TARGET_TEST_DIR}/efivarfs"
FILES_${PN}-exec = "${TARGET_TEST_DIR}/exec"
FILES_${PN}-filesystems = "${TARGET_TEST_DIR}/filesystems"
FILES_${PN}-firmware = "${TARGET_TEST_DIR}/firmware"
FILES_${PN}-ftrace = "${TARGET_TEST_DIR}/ftrace"
FILES_${PN}-futex = "${TARGET_TEST_DIR}/futex"
FILES_${PN}-ipc = "${TARGET_TEST_DIR}/ipc"
FILES_${PN}-ir = "${TARGET_TEST_DIR}/ir"
FILES_${PN}-kcmp = "${TARGET_TEST_DIR}/kcmp"
FILES_${PN}-lib = "${TARGET_TEST_DIR}/lib"
FILES_${PN}-livepatch = "${TARGET_TEST_DIR}/livepatch"
FILES_${PN}-lkdtm = "${TARGET_TEST_DIR}/lkdtm"
FILES_${PN}-membarrier = "${TARGET_TEST_DIR}/membarrier"
FILES_${PN}-memory-hotplug = "${TARGET_TEST_DIR}/memory-hotplug"
FILES_${PN}-mount = "${TARGET_TEST_DIR}/mount"
FILES_${PN}-mqueue = "${TARGET_TEST_DIR}/mqueue"
FILES_${PN}-net = "${TARGET_TEST_DIR}/net"
FILES_${PN}-networking = "${TARGET_TEST_DIR}/networking"
FILES_${PN}-powerpc = "${TARGET_TEST_DIR}/powerpc"
FILES_${PN}-proc = "${TARGET_TEST_DIR}/proc"
FILES_${PN}-pstore = "${TARGET_TEST_DIR}/pstore"
FILES_${PN}-ptrace = "${TARGET_TEST_DIR}/ptrace"
FILES_${PN}-rtc = "${TARGET_TEST_DIR}/rtc"
FILES_${PN}-seccomp = "${TARGET_TEST_DIR}/seccomp"
FILES_${PN}-sigaltstack = "${TARGET_TEST_DIR}/sigaltstack"
FILES_${PN}-size = "${TARGET_TEST_DIR}/size"
FILES_${PN}-sparc64 = "${TARGET_TEST_DIR}/sparc64"
FILES_${PN}-splice = "${TARGET_TEST_DIR}/splice"
FILES_${PN}-static-keys = "${TARGET_TEST_DIR}/static_keys"
FILES_${PN}-sync = "${TARGET_TEST_DIR}/sync"
FILES_${PN}-sysctl = "${TARGET_TEST_DIR}/sysctl"
FILES_${PN}-timers = "${TARGET_TEST_DIR}/timers"
FILES_${PN}-tmpfs = "${TARGET_TEST_DIR}/tmpfs"
FILES_${PN}-tpm2 = "${TARGET_TEST_DIR}/tpm2"
FILES_${PN}-user = "${TARGET_TEST_DIR}/user"
FILES_${PN}-vm = "${TARGET_TEST_DIR}/vm"
FILES_${PN}-x86 = "${TARGET_TEST_DIR}/x86"
FILES_${PN}-zram = "${TARGET_TEST_DIR}/zram"
FILES_${PN}-dbg += " ${TARGET_TEST_DIR}/*/.debug"
FILES_${PN} += " ${TARGET_TEST_DIR}"

# FIXME bpf target is failing to build and need to be fixed:
# In file included from test_verifier.c:23:0:
# ../../../../usr/include/linux/bpf_perf_event.h:14:17: error: field 'regs' has incomplete type
#   struct pt_regs regs;
#                  ^~~~
# make[1]: *** [test_verifier] Error 1
ALLOW_EMPTY_${PN}-bpf = "1"

# gcc 7.x fails to build seccomp
ALLOW_EMPTY_${PN}-seccomp = "1"

# FIXME net target builds most of the binaries, but reuseport_bpf_numa depends on libnuma,
# which is not availbale on ARM, failing entire test case
ALLOW_EMPTY_${PN}-net = "1"

RDEPENDS_${PN}-cpu-hotplug += "bash"
RDEPENDS_${PN}-efivarfs += "bash"
RDEPENDS_${PN}-futex += "bash ncurses"
RDEPENDS_${PN}-memory-hotplug += "bash"
RDEPENDS_${PN}-net += "bash"
RDEPENDS_${PN}-vm += "bash sudo"
RDEPENDS_${PN}-zram += "bash bc"
RDEPENDS_${PN} += "bash \
	${PN}-android \
	${PN}-bpf \
	${PN}-capabilities \
	${PN}-clone3 \
	${PN}-cpufreq \
	${PN}-cpu-hotplug \
	${PN}-drivers \
	${PN}-efivarfs \
	${PN}-exec \
	${PN}-filesystems \
	${PN}-firmware \
	${PN}-ftrace \
	${PN}-futex \
	${PN}-ir \
	${PN}-kcmp \
	${PN}-lib \
	${PN}-livepatch \
	${PN}-lkdtm \
	${PN}-membarrier \
	${PN}-memory-hotplug \
	${PN}-mount \
	${PN}-mqueue \
	${PN}-net \
	${PN}-networking \
	${PN}-proc \
	${PN}-pstore \
	${PN}-ptrace \
	${PN}-rtc \
	${PN}-seccomp \
	${PN}-sigaltstack \
	${PN}-size \
	${PN}-splice \
	${PN}-static-keys \
	${PN}-sync \
	${PN}-sysctl \
	${PN}-timers \
	${PN}-tmpfs \
	${PN}-tpm2 \
	${PN}-user \
	${PN}-vm \
	${PN}-zram \
"

RDEPENDS_${PN}_append_aarch64 = " ${PN}-breakpoints ${PN}-ipc ${PN}-arm64"
RDEPENDS_${PN}_append_x86 = " ${PN}-breakpoints ${PN}-ipc ${PN}-x86"
RDEPENDS_${PN}_append_x86-64 = " ${PN}-breakpoints ${PN}-ipc ${PN}-x86"
RDEPENDS_${PN}_append_powerpc = " ${PN}-powerpc"
RDEPENDS_${PN}_append_powerpc64 = " ${PN}-powerpc"
RDEPENDS_${PN}_append_sparc64 = " ${PN}-sparc64"

INSANE_SKIP_${PN} = "already-stripped"
INSANE_SKIP_${PN}-exec = "ldflags"
INSANE_SKIP_${PN}-ipc = "ldflags"
INSANE_SKIP_${PN}-mount = "ldflags"
INSANE_SKIP_${PN}-vm = "ldflags"
