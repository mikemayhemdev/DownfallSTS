package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class CloakOfManyFaces extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("CloakOfManyFaces");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/CloakOfManyFaces.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/CloakOfManyFaces.png"));

    public CloakOfManyFaces() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new CultistMask());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new FaceOfCleric());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new NlothsMask());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new GremlinMask());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new SsserpentHead());

    }
}
