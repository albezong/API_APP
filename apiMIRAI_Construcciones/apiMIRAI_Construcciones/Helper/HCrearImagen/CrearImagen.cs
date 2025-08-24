using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Windows;
using System.Windows.Forms;

namespace APIMIRAI_Construcciones.Helper.HCrearImagen
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
                    string query = "SELECT * FROM QrEquipos WHERE id = @id";

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
                            var Xm = 0;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                System.Windows.MessageBox.Show($"Error al cargar la imagen: {ex.Message}");
            }
        }
    }
}
