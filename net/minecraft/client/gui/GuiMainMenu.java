package net.minecraft.client.gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import me.superskidder.lune.GLSLSandboxShader;
import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.login.GuiAltManager;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// TODO: 2021/2/28 进入世界后mainmenu背景会失效 
public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    public static String NOTICE;
    private static int logoanimy = -10;
    private String wallpaperPath = new ResourceLocation("client/UI/bg_blur.png").getResourcePath();
    private static GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();

    public GuiMainMenu() {
        if (Lune.needReload) {
            // Clean Module Manager
            for (Mod mod : ModuleManager.pluginModsList.keySet()) {
                mod.setStage(false);
                ModuleManager.modList.remove(mod);
            }
            ModuleManager.pluginModsList.clear();

            // Clean Command Manager
            for (Command cmd : CommandManager.pluginCommands.keySet()) {
                CommandManager.commands.remove(cmd);
            }
            CommandManager.pluginCommands.clear();

            Lune.pluginManager.plugins.clear();
            Lune.pluginManager.urlCL.clear();

            // Reload
            Lune.pluginManager.loadPlugins(true);
            Lune.scriptManager.loadScripts();
            Lune.needReload = false;
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {
//        if (result) {
//            mc.displayGuiScreen(new GuiAltManager());
//        } else {
//            AutoUpdate.needOpenUpdateMenu = false;
//            mc.displayGuiScreen(new GuiMainMenu());
//        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {

    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    public void initGui() {
        logoanimy = -10;
        try {
            this.backgroundShader = new GLSLSandboxShader("/shader.fsh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTime = System.currentTimeMillis();

    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        if (AutoUpdate.needOpenUpdateMenu) {
//            GuiYesNo ask = new GuiYesNo(this, "发现新版本", "Lune客户端检测到新版本:Lune" + AutoUpdate.webVer + "更新需要内测账号，是否更新？", "Update", "Back", 0);
//            mc.displayGuiScreen(ask);
//        }
        ScaledResolution sr = new ScaledResolution(mc);
        //RenderUtils.drawImage(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new ResourceLocation("client/UI/skybg.png"));

        this.drawDefaultBackground();
        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY,initTime / 1000f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f,-1f);
        GL11.glVertex2f(-1f,1f);
        GL11.glVertex2f(1f,1f);
        GL11.glVertex2f(1f,-1f);
        GL11.glEnd();

        GL20.glUseProgram(0);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

//        RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(38, 38, 38, 50).getRGB());

        //RenderUtils.drawImage((int) (sr.getScaledWidth_double() / 2 - 46), (int) (sr.getScaledHeight_double() / 2 - 115)+logoanimy, 32, 32, new ResourceLocation("client/Lune.png"), new Color(43,113,177));
        //FontLoaders.F40.drawCenteredString("Lune", (float) sr.getScaledWidth_double() / 2+16, (float) sr.getScaledHeight_double() / 2 - 110+logoanimy,new Color(43,113,177).getRGB());
        FontLoaders.Logo.drawCenteredStringWithShadow(Lune.CLIENT_NAME + " " + Lune.CLIENT_Ver, (float) sr.getScaledWidth_double() / 2, (float) sr.getScaledHeight_double() / 2 - 110, new Color(255, 255, 255).getRGB());


//        RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 81), (int) (sr.getScaledHeight_double() / 2 - 81) , (int) (sr.getScaledWidth_double() / 2) + 81, (int) (sr.getScaledHeight_double() / 2 + 76) , new Color(52, 52, 52).getRGB());
//        RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 80), (int) (sr.getScaledHeight_double() / 2 - 80) , (int) (sr.getScaledWidth_double() / 2) + 80, (int) (sr.getScaledHeight_double() / 2 + 75) , new Color(33, 33, 33).getRGB());


        String[] S = new String[]{"SinglePlayer", "MuiltPlayer", "Alts", "Option", "ShutDown"};

        for (int i = 0; i < 5; i++) {
            RenderUtils.drawRect((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY) ? new Color(0, 0, 0, 179).getRGB() : new Color(0, 0, 0, 77).getRGB());
            FontLoaders.F20.drawCenteredStringWithShadow(S[i], (float) sr.getScaledWidth_double() / 2, (float) sr.getScaledHeight_double() / 2 - 66 + i * 30, isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY) ? new Color(255, 255, 255).getRGB() : new Color(255, 255, 255).getRGB());
        }

        //RenderUtils.drawImage((int) sr.getScaledWidth_double() / 2 - 45, (int) sr.getScaledHeight_double() / 2 + 20+logoanimy, 16, 16, new ResourceLocation("client/UI/account.png"), isHovered((int) sr.getScaledWidth_double() / 2 - 80, (int) sr.getScaledHeight_double() / 2 + 20, (int) sr.getScaledWidth_double() / 2 - 80 + 160, (int) sr.getScaledHeight_double() / 2 + 20 + 15, mouseX, mouseY) ? new Color(200, 200, 200) : new Color(255, 255, 255));
        //FontLoaders.F18.drawString("WallPaper", (float) sr.getScaledWidth_double() / 2 - 20, (float) sr.getScaledHeight_double() / 2 + 50+logoanimy, isHovered((int) sr.getScaledWidth_double() / 2 - 80, (int) sr.getScaledHeight_double() / 2 + 45, (int) sr.getScaledWidth_double() / 2 - 80 + 160, (int) sr.getScaledHeight_double() / 2 + 45  + 15, mouseX, mouseY) ? new Color(200, 200, 200).getRGB() : new Color(255, 255, 255).getRGB());
        FontLoaders.F16.drawString("Lune " + Lune.CLIENT_Ver, 10, sr.getScaledHeight() - FontLoaders.F16.getStringHeight(" ") - 8, new Color(220, 220, 220).getRGB());
        //FontLoaders.F16.drawString(NOTICE, (float) (sr.getScaledWidth_double() / 2 - FontLoaders.F16.getStringWidth(NOTICE) / 2), 10, new Color(255, 255, 255, 100).getRGB());


//        FontLoaders.F16.drawString("Development - SuperSkidder & MarShall", sr.getScaledWidth() - FontLoaders.F16.getStringWidth("Development - SuperSkidder & MarShall") - 10, sr.getScaledHeight() - FontLoaders.F16.getStringHeight(" ") - 10, new Color(43,113,177).getRGB());
        //Lune.fontManager.Chinese16.drawStringWithShadow("你好！2233",5,5,new Color(0,0,0).getRGB());

    }

    public boolean isHovered(double x, double y, float x2, float y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2) {
            return true;
        }

        return false;
    }

    /**
     * Called when the mouse is cliced. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        ScaledResolution sr = new ScaledResolution(mc);


        for (int i = 0; i < 5; i++) {
            if (isHovered((int) (sr.getScaledWidth_double() / 2 - 75), (int) (sr.getScaledHeight_double() / 2 - 75 + i * 25 + i * 5), (int) (sr.getScaledWidth_double() / 2) + 75, (int) (sr.getScaledHeight_double() / 2 - 50 + i * 25 + i * 5), mouseX, mouseY)) {
                switch (i) {
                    case 0:
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case 1:
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case 2:
                        mc.displayGuiScreen(new GuiAltManager());
                        break;
                    case 3:
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case 4:
                        mc.shutdown();
                        break;
                    default:
                        break;

                }
            }
        }


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {

    }
}
