<?php session_start();
    if (isset($_POST['registrasi'])){

		$mydb = new mysqli('localhost', 'root','','laundry');

		$sql = "INSERT INTO tbl_user (email, password, nama_laundry, nama_pemilik, alamat, tahun_berdirirga_perkilo)
				VALUES ('$_POST[email]', '$_POST[password]', '$_POST[nama_laundry]', '$_POST[nama_pemilik]', '$_POST[alamat]', '$_POST[tahun_berdiri]', '$_POST[harga_perkilo]')";
				
		if ($mydb->query($sql) == TRUE) {
			echo header("location:registrasi.php");
		} else {
			echo "INSERT attempt failed, please try again later, or call tech support";
			
			$mydb->close();
		}
    }
?>