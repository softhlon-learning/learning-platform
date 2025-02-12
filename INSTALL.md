# System Intallation

## Install FreeBSD base system
 * Enable Rescue (Linux with auth key) mode in Hetzner console
 * Restart server
 * SSH to rescue server (ssh root@server)
 * Download FreeBSD images
   * curl -O http://ftp2.de.freebsd.org/pub/FreeBSD/releases/amd64/14.1-RELEASE/base.txz
   * curl -O http://ftp2.de.freebsd.org/pub/FreeBSD/releases/amd64/14.1-RELEASE/kernel.txz
   * curl -O https://mfsbsd.vx.sk/files/iso/14/amd64/mfsbsd-14.1-RELEASE-amd64.iso
 * Cleanup if needed
   * lsblk (to double check disk names)
   * cfdisk /dev/nvme0n1 (delete all partitions)
   * cfdisk /dev/nvme0n1 (delete all partitions)
   * mkfs -t ext4 /dev/nvme0n1
   * mkfs -t ext4 /dev/nvme0n1
 * Run QEMU and set VNC password
   * qemu-system-x86_64 -net nic -net user,hostfwd=tcp::1022-:22 -m 2048M -enable-kvm \
     -cpu host,+nx -M pc -smp 2 -vga std -k en-us \
     -cdrom ./mfsbsd-14.0-RELEASE-amd64.iso \
     -device virtio-scsi-pci,id=scsi0 \
     -drive file=/dev/nvme0n1,if=none,format=raw,discard=unmap,aio=native,cache=none,id=n0 \
     -device scsi-hd,drive=n0,bus=scsi0.0 \
     -drive file=/dev/nvme1n1,if=none,format=raw,discard=unmap,aio=native,cache=none,id=n1 \
     -device scsi-hd,drive=n1,bus=scsi0.0 \
     -boot once=d -vnc 127.0.0.1:0,password=on -monitor stdio
   * (qemu) set_password vnc mfsroot
 * Copy the distribution files to the VM (from another terminal windown on rescue linux)
   * scp -o Port=1022 base.txz kernel.txz root@localhost:
 * Login to the VM
   * ssh -p 1022 root@localhost
 * zfsinstall -d /dev/da0 -d /dev/da1 -r mirror -p zroot -s 16G -u .
 * mount -t devfs devfs /mnt/dev
 * chroot /mnt

   

   
   



