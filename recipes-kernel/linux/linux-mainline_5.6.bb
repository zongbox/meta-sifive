require recipes-kernel/linux/linux-mainline-common.inc

LINUX_VERSION ?= "5.6"
KERNEL_VERSION_SANITY_SKIP="1"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;tag=v5.6 \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI_append_freedom-u540 = " \
    file://0001-PCI-microsemi-Add-host-driver-for-Microsemi-PCIe-con.patch \
    file://0002-Microsemi-PCIe-expansion-board-DT-entry.patch \
    file://0003-HACK-Revert-of-device-Really-only-set-bus-DMA-mask-w.patch \
    file://0004-SiFive-Unleashed-CPUFreq.patch \
    file://0007-Add-PWM-LEDs-D1-D2-D3-D4.patch \
    file://riscv-add-support-to-determine-no-of-L2-cache-way-enabled.patch \
    file://0001-perf-fix-Clang-version-check-for-OE.patch \
"

# For freedom-u540 use out-of-tree defconfig
# The upstream defconfig might contain a large number of debug options, which
# could affect the performance.
SRC_URI_append_freedom-u540 = " file://defconfig"
