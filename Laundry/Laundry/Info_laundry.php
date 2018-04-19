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
    <?php

      $id_user = $_REQUEST['id_user'];
      $mydb = new mysqli('localhost','root','','laundry');
      $sql = "SELECT * FROM tbl_user WHERE id_user = '$id_user'";
      $result = $mydb->query($sql);
    ?>
  <body>
    <?php
      while($fetch = $result->fetch_array()){ 
    ?>
    <style>
      .demo-card-header-pic .card-header {
        height: 40vw;
        background-size: cover;
        background-position: center;
      }
    </style>
     
    <div class="content-block-title">Styled Cards</div>
     
    <div class="card demo-card-header-pic">
      <div style="background-image:url(...)" valign="bottom" class="card-header color-white no-border">Journey To Mountains</div>
      <div class="card-content">
        <div class="card-content-inner">
          <div class="list-block">
            <ul>
              <!-- Text inputs -->
              <li>
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">Nama Laundry</div>
                    <div class="item-input">
                      <div class="item-title label"><?php echo $fetch['nama_laundry']; ?></div>
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
                      <div class="item-title label"><?php echo $fetch['nama_pemilik']; ?></div>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">E-mail</div>
                    <div class="item-input">
                      <div class="item-title label"><?php echo $fetch['email']; ?></div>
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
                      <textarea><?php echo $fetch['alamat']; ?></textarea>
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
                      <div class="item-title label"><?php echo $fetch['tahun_berdiri']; ?></div>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">Harga Perkilo</div>
                    <div class="item-input">
                      <div class="item-title label"><?php echo $fetch['harga_perkilo']; ?></div>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div>
                  <div>
                    <a href="list_laundry.php">Kembali</a>  
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <?php
      }
    ?>
  </body>
</html>