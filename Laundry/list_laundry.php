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

      $mydb = new mysqli('localhost','root','','laundry');
      $sql = "SELECT * FROM tbl_user";
      $result = $mydb->query($sql);
    ?>
  <body>
    <div>
      <p> LIST LAUNDRY</p>
    </div>    
    <div class="list-block">
      <ul>
      <?php
        while($fetch = $result->fetch_array()){ 
      ?>
        <li>
          <a href="info_laundry.php?id_user=<?php echo $fetch['id_user']; ?>" class="item-link">
            <div class="page-content contacts-content">
              <div class="list-block contacts-block">
                <ul>
                  <div class="item-content">
                    <div class="item-inner">
                      <div class="item-title"><?php echo $fetch['nama_laundry']; ?></div>
                    </div>
                  </div>
                </ul>
              </div>
            </div>
          </a>
        </li>
        <?php
          }
        ?>
      </ul>
      <div class="list-block-label">List block label text goes here</div>
    </div>
  </body>
</html>