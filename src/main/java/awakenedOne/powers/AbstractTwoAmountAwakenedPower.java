package awakenedOne.powers;

import awakenedOne.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

import static awakenedOne.AwakenedOneMod.getModID;

public abstract class AbstractTwoAmountAwakenedPower extends TwoAmountPower {
    protected static Map<String, PowerStrings> powerStrings = new HashMap<>();
    protected AbstractCreature source;
    protected String[] DESCRIPTIONS;

    public AbstractTwoAmountAwakenedPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        this(NAME, powerType, isTurnBased, owner, source, amount, "");
    }

    public AbstractTwoAmountAwakenedPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, String IDModifier) {
        this.ID = makeID(NAME);
        this.isTurnBased = isTurnBased;

        if (!powerStrings.containsKey(this.ID))
            powerStrings.put(this.ID, getPowerStrings(this.ID));
        this.name = powerStrings.get(this.ID).NAME;
        this.DESCRIPTIONS = powerStrings.get(this.ID).DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        Texture normalTexture = TexLoader.getTexture("awakenedResources/images/powers/" + NAME + "32.png");
        Texture hiDefImage = TexLoader.getTexture("awakenedResources/images/powers/" + NAME + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }

        this.ID += IDModifier;

        this.updateDescription();
    }

    public AbstractTwoAmountAwakenedPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean loadImage) {
        this.ID = makeID(NAME);
        this.isTurnBased = isTurnBased;

        if (!powerStrings.containsKey(this.ID))
            powerStrings.put(this.ID, getPowerStrings(this.ID));
        this.name = powerStrings.get(this.ID).NAME;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        if (loadImage) {
            Texture normalTexture = TexLoader.getTexture("awakenedResources/images/powers/" + NAME + "32.png");
            Texture hiDefImage = TexLoader.getTexture("awakenedResources/images/powers/" + NAME + "84.png");
            if (hiDefImage != null) {
                region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                if (normalTexture != null)
                    region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            } else if (normalTexture != null) {
                this.img = normalTexture;
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
        }

        this.updateDescription();
    }

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    public void applyToSelf(AbstractPower po) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }
}