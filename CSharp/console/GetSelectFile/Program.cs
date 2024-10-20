namespace GetSelectFile;

internal static class Program
{
    [STAThread]
    private static void Main(string[] args)
    {
        var dialog = new FolderBrowserDialog();
        var result = dialog.ShowDialog();
        Console.WriteLine(result == DialogResult.OK ? $"选择的文件夹为: {dialog.SelectedPath}" : "请选择一个合法文件夹");

        var openFileDialog = new OpenFileDialog();
        var oResult = openFileDialog.ShowDialog();
        Console.WriteLine(oResult == DialogResult.OK ? $"选择的文件为: {openFileDialog.FileName}" : "请打开一个合法文件");

        Console.WriteLine("按任意键退出");
        Console.ReadKey();
    }
}