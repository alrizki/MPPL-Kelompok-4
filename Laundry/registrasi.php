<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags-->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- Your app title -->
    <title>My App</title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
  </head>
  <body>
    <form method="POST" action="input_registrasi.php">
    <div class="content-block-title">FORM REGISTRASI LAUNDRY</div>
    <div class="list-block">
      <ul>
        <!-- Text inputs -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Nama Laundry</div>
              <div class="item-input">
                <input type="text" placeholder="Nama Laundry" name="nama_laundry">
              </div>
            </div>
          </div>
        </li>
        <!-- Text inputs -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Nama Pemilik</div>
              <div class="item-input">
                <input type="text" placeholder="Nama Pemilik" name="nama_pemilik">
              </div>
            </div>
          </div>
        </li>
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">E-mail</div>
              <div class="item-input">
                <input type="email" placeholder="E-mail" name="email">
              </div>
            </div>
          </div>
        </li>
         <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Password</div>
              <div class="item-input">
                <input type="Password" placeholder="Password" name="password">
              </div>
            </div>
          </div>
        </li>
        <!-- Textarea -->
        <li class="align-top">
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Alamat</div>
              <div class="item-input">
                <textarea name="alamat"></textarea>
              </div>
            </div>
          </div>
        </li>
        <!-- Date -->
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Tahun Berdiri</div>
              <div class="item-input">
                <input type="date" placeholder="Tahun Berdiiri" value="2014-04-30" name="tahun_berdiri">
              </div>
            </div>
          </div>
        </li>
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">Harga Perkilo</div>
              <div class="item-input">
                <input type="text" placeholder="Harga Perkilo" name="harga_perkilo">
              </div>
            </div>
          </div>
        </li>
        <li>
          <div>
            <div>
              <p><input type="submit" name="registrasi" class="button button-round active" value="Registrasi"></p>    
              <p><input type="reset" class="button button-round active" value="Reset"></p>    
            </div>
          </div>
        </li>
      </ul>
    </div>
  </form>
  </body>
</html>