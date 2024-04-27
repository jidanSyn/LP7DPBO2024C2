# LP7DPBO2024C2
## Flappy Bird dengan Java Swing GUI
### Desain Program
Program terdiri dari 5 kelas, App, Menu, FlappyBird, Pipe, Player.
- Player
  Kelas yang menampung semua attribut Player atau burung dalam game, seperti posisi x dan y, kecepatan y untuk pergerakan vertikal player, image yang merepresentasikan player, serta keterangan panjang dan lebar player.
- Pipa
  Kelas yang menampung semua attribut Pipa yaitu halangan dalam game yang harus dilewati, posisi x & y, image, panjang & lebar, lalu kecepatan x yang bernilai negatif untuk pergerakan dari kanan ke kiri, juga boolean passed untuk menandakan apakah sudah dilewati player
- FlappyBird
  Kelas yang menampung seluruh attribut juga logika terkait game
- Menu
  Kelas tampilan menu, akan membuat game FlappyBird dan menutup diri sendirinya
- App
  Kelas utama yang mengandung main dan awalnya akan membuat dan menampilkan layar menu
  
### Penjelasan Alur
- Program masuk ke Menu, memiliki tombol untuk memulai Game
- Setelah diklik tombol Start, akan memulai Game Flappy Bird, dimana burung dapat dikontrol dengan spasi untuk terbang ke atas, jika burung menabrak pipa atau jatuh ke bawah, game akan selesai, dan ketika melewati pipa akan menambah skor
- Ketika Game Over, dapat memulai ulang game dengan menekan R
### Screen Record
[Screencast from 27-04-24 11:43:35.webm](https://github.com/jidanSyn/LP7DPBO2024C2/assets/114399924/abc2c657-b5e9-4b36-9566-b0e6ce841ba2)
