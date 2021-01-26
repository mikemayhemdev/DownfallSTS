package theTodo.util;

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
import theTodo.TodoMod;
import theTodo.cards.AbstractEasyCard;

import java.util.HashMap;

import static theTodo.TodoMod.colorCardsPrefs;

public class CardArtRoller {
    public static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap<>();
    public static HashMap<String, ReskinInfo> infos = new HashMap<String, ReskinInfo>();
    private static ShaderProgram shade = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   v_color.a = pow(v_color.a * (255.0/254.0) + 0.5, 1.709);\n   v_lightFix = 1.0 + pow(v_color.a, 1.41421356);\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\nvarying LOWP vec4 v_color;\nuniform sampler2D u_texture;\nconst float eps = 1.0e-10;\nvec4 rgb2hsl(vec4 c)\n{\n    const vec4 J = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n    vec4 p = mix(vec4(c.bg, J.wz), vec4(c.gb, J.xy), step(c.b, c.g));\n    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n    float d = q.x - min(q.w, q.y);\n    float l = q.x * (1.0 - 0.5 * d / (q.x + eps));\n    return vec4(abs(q.z + (q.w - q.y) / (6.0 * d + eps)), (q.x - l) / (min(l, 1.0 - l) + eps), l, c.a);\n}\n\nvec4 hsl2rgb(vec4 c)\n{\n    const vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n    vec3 p = abs(fract(c.x + K.xyz) * 6.0 - K.www);\n    float v = (c.z + c.y * min(c.z, 1.0 - c.z));\n    return vec4(v * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), 2.0 * (1.0 - c.z / (v + eps))), c.w);\n}void main()\n{\n    float hue = (v_color.x - 0.5);\n    float saturation = v_color.y * 2.0;\n    float brightness = v_color.z - 0.5;\n    vec4 tgt = texture2D( u_texture, v_texCoords );\n    tgt = rgb2hsl(tgt);\n    tgt.r = fract(tgt.r + hue);\n    tgt = hsl2rgb(tgt);\n    tgt.rgb = vec3(\n     (0.5 * pow(dot(tgt.rgb, vec3(0.375, 0.5, 0.125)), v_color.w) * v_lightFix + brightness),\n     ((tgt.r - tgt.b) * saturation),\n     ((tgt.g - tgt.b) * saturation));\n    gl_FragColor = clamp(vec4(\n     dot(tgt.rgb, vec3(1.0, 0.625, -0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, 0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, -0.5)),\n     tgt.a), 0.0, 1.0);\n}");

    public static void computeCard(AbstractEasyCard c) {
        c.portrait = doneCards.computeIfAbsent(c.cardID, key -> {
            ReskinInfo r = infos.computeIfAbsent(key, key2 -> {
                if (!colorCardsPrefs.getString(key2 + "origID").equals("")) {
                    ReskinInfo s = new CardArtRoller.ReskinInfo(
                            colorCardsPrefs.getString(key2 + "origID"),
                            colorCardsPrefs.getFloat(key2 + "hue", 0.0f),
                            colorCardsPrefs.getFloat(key2 + "sat", 0.0f),
                            colorCardsPrefs.getFloat(key2 + "lig", 0.0f),
                            colorCardsPrefs.getFloat(key2 + "con", 0.0f),
                            colorCardsPrefs.getBoolean(key2 + "flip"));
                    return s;
                } else {
                    ReskinInfo s = TodoMod.getNewReskinInfo(c.type);
                    return s;
                }
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
