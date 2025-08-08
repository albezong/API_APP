using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Windows;
using System.Windows.Forms;

namespace apiMIRAI_Construcciones.Helper.HCrearImagen
{
    public class CrearImagen
    {
    }

    public class ImagenDesdeSQL
    {
        public static void MostrarImagen(string connectionString, int idImagen, PictureBox pictureBox)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    string query = "SELECT * FROM QrEquipos WHERE id = @id"; // Reemplaza 'tuTabla' y 'id'
                    //SELECT* FROM QrEquipos WHERE idQrEquipos = 1;

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@id", idImagen);
                        byte[] imageData = (byte[])command.ExecuteScalar();

                        if (imageData != null)
                        {
                            using (MemoryStream ms = new MemoryStream(imageData))
                            {
                                pictureBox.Image = Image.FromStream(ms);
                            }
                        }
                        else
                        {
                            // Manejar el caso donde no se encuentra la imagen
                            //MessageBox.Silent("Imagen no encontrada.");
                            //pictureBox.Image = null; // Limpiar el PictureBox si no hay imagen
                            var Xm = 0;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                // Manejar errores de la base de datos o conversión
                System.Windows.MessageBox.Show($"Error al cargar la imagen: {ex.Message}");
            }
        }
    }
}
