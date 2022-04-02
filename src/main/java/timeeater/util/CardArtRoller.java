package timeeater.util;

import basemod.patches.whatmod.WhatMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import timeeater.cards.AbstractTimeEaterCard;

import java.util.ArrayList;
import java.util.HashMap;

public class CardArtRoller {
    public static final String partialHueRodrigues =
            "vec3 applyHue(vec3 rgb, float hue)\n" +
                    "{\n" +
                    "    vec3 k = vec3(0.57735);\n" +
                    "    float c = cos(hue);\n" +
                    "    //Rodrigues' rotation formula\n" +
                    "    return rgb * c + cross(k, rgb) * sin(hue) + k * dot(k, rgb) * (1.0 - c);\n" +
                    "}\n";
    public static final String vertexShaderHSLC = "attribute vec4 a_position;\n"
            + "attribute vec4 a_color;\n"
            + "attribute vec2 a_texCoord0;\n"
            + "uniform mat4 u_projTrans;\n"
            + "varying vec4 v_color;\n"
            + "varying vec2 v_texCoords;\n"
            + "varying float v_lightFix;\n"
            + "\n"
            + "void main()\n"
            + "{\n"
            + "   v_color = a_color;\n"
            + "   v_texCoords = a_texCoord0;\n"
            + "   v_color.a = pow(v_color.a * (255.0/254.0) + 0.5, 1.709);\n"
            + "   v_lightFix = 1.0 + pow(v_color.a, 1.41421356);\n"
            + "   gl_Position =  u_projTrans * a_position;\n"
            + "}\n";

    public static final String fragmentShaderHSLC =
            "#ifdef GL_ES\n" +
                    "#define LOWP lowp\n" +
                    "precision mediump float;\n" +
                    "#else\n" +
                    "#define LOWP \n" +
                    "#endif\n" +
                    "varying vec2 v_texCoords;\n" +
                    "varying float v_lightFix;\n" +
                    "varying LOWP vec4 v_color;\n" +
                    "uniform sampler2D u_texture;\n" +
                    partialHueRodrigues +
                    "void main()\n" +
                    "{\n" +
                    "    float hue = 6.2831853 * (v_color.x - 0.5);\n" +
                    "    float saturation = v_color.y * 2.0;\n" +
                    "    float brightness = v_color.z - 0.5;\n" +
                    "    vec4 tgt = texture2D( u_texture, v_texCoords );\n" +
                    "    tgt.rgb = applyHue(tgt.rgb, hue);\n" +
                    "    tgt.rgb = vec3(\n" +
                    "     (0.5 * pow(dot(tgt.rgb, vec3(0.375, 0.5, 0.125)), v_color.w) * v_lightFix + brightness),\n" + // lightness
                    "     ((tgt.r - tgt.b) * saturation),\n" + // warmth
                    "     ((tgt.g - tgt.b) * saturation));\n" + // mildness
                    "    gl_FragColor = clamp(vec4(\n" +
                    "     dot(tgt.rgb, vec3(1.0, 0.625, -0.5)),\n" + // back to red
                    "     dot(tgt.rgb, vec3(1.0, -0.375, 0.5)),\n" + // back to green
                    "     dot(tgt.rgb, vec3(1.0, -0.375, -0.5)),\n" + // back to blue
                    "     tgt.a), 0.0, 1.0);\n" + // keep alpha, then clamp
                    "}";

    private static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap<>();
    public static HashMap<String, ReskinInfo> infos = new HashMap<String, ReskinInfo>();
    private static ShaderProgram shade = new ShaderProgram(vertexShaderHSLC, fragmentShaderHSLC);
    public static ArrayList<String> attacksList = new ArrayList<>();
    public static ArrayList<String> skillsList = new ArrayList<>();
    public static ArrayList<String> powersList = new ArrayList<>();

    public static void computeCard(AbstractTimeEaterCard c) {
        c.portrait = doneCards.computeIfAbsent(c.cardID, key -> {
            ReskinInfo r = infos.computeIfAbsent(key, key2 -> {
                Random rng = new Random((long) c.cardID.hashCode());
                String q;
                if (c.cardArtCopy() != null) {
                    q = c.cardArtCopy();
                } else {
                    ArrayList<AbstractCard> cardsList;
                    switch (c.type) {
                        case ATTACK:
                            if (attacksList.isEmpty()) {
                                for (AbstractCard q2 : Wiz.getCardsMatchingPredicate((s) -> s.type == AbstractCard.CardType.ATTACK &&
                                        WhatMod.findModName(s.getClass()) == null, true)) {
                                    attacksList.add(q2.cardID);
                                }
                            }
                            q = Wiz.getRandomItem(attacksList, rng);
                            break;
                        case POWER:
                            if (powersList.isEmpty()) {
                                for (AbstractCard q2 : Wiz.getCardsMatchingPredicate((s) -> s.type == AbstractCard.CardType.POWER &&
                                        WhatMod.findModName(s.getClass()) == null, true)) {
                                    powersList.add(q2.cardID);
                                }
                            }
                            q = Wiz.getRandomItem(powersList, rng);
                            break;
                        default:
                            if (skillsList.isEmpty()) {
                                for (AbstractCard q2 : Wiz.getCardsMatchingPredicate((s) -> s.type != AbstractCard.CardType.ATTACK && s.type != AbstractCard.CardType.POWER &&
                                        WhatMod.findModName(s.getClass()) == null, true)) {
                                    skillsList.add(q2.cardID);
                                }
                            }
                            q = Wiz.getRandomItem(skillsList, rng);
                            break;
                    }
                }
                return new ReskinInfo(q, rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.randomBoolean());
            });
            Color HSLC = new Color(r.H, r.S, r.L, r.C);
            TextureAtlas.AtlasRegion t = CardLibrary.getCard(r.origCardID).portrait;
            t.flip(false, true);
            FrameBuffer fb = ImageHelper.createBuffer(250, 190);
            OrthographicCamera og = new OrthographicCamera(250, 190);
            SpriteBatch sb = new SpriteBatch();
            sb.setProjectionMatrix(og.combined);
            ImageHelper.beginBuffer(fb);
            sb.setShader(shade);
            sb.setColor(HSLC);
            sb.begin();
            sb.draw(t, -125, -95);
            sb.end();
            fb.end();
            t.flip(false, true);
            TextureRegion a = ImageHelper.getBufferTexture(fb);
            return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
        });
    }

    public static Texture getPortraitTexture(AbstractCard c) {
        ReskinInfo r = infos.get(c.cardID);
        Color HSLC = new Color(r.H, r.S, r.L, r.C);
        TextureAtlas.AtlasRegion t = new TextureAtlas.AtlasRegion(TexLoader.getTexture("images/1024Portraits/" + CardLibrary.getCard(r.origCardID).assetUrl + ".png"), 0, 0, 500, 380);
        t.flip(false, true);
        FrameBuffer fb = ImageHelper.createBuffer(500, 380);
        OrthographicCamera og = new OrthographicCamera(500, 380);
        SpriteBatch sb = new SpriteBatch();
        sb.setProjectionMatrix(og.combined);
        ImageHelper.beginBuffer(fb);
        sb.setShader(shade);
        sb.setColor(HSLC);
        sb.begin();
        sb.draw(t, -250, -190);
        sb.end();
        fb.end();
        t.flip(false, true);
        TextureRegion a = ImageHelper.getBufferTexture(fb);
        return a.getTexture();

        //Actually, I think this can work. Because SingleCardViewPopup disposes of the texture, we can just make a new one every time.
    }

    public static class ReskinInfo {
        public String origCardID;
        public float H;
        public float S;
        public float L;
        public float C;
        public boolean flipX;

        public ReskinInfo(String ID, float H, float S, float L, float C, boolean flipX) {
            this.origCardID = ID;
            this.H = H;
            this.S = S;
            this.L = L;
            this.C = C;
            this.flipX = flipX;
        }
    }
}
