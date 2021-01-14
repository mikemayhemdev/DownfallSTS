package automaton.powers;

import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

import static automaton.AutomatonMod.getModID;
import static automaton.AutomatonMod.makeID;


public abstract class AbstractAutomatonPower extends AbstractPower {
    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    protected AbstractCreature source;

    protected static Map<String, PowerStrings> powerStrings = new HashMap<>();

    protected String[] DESCRIPTIONS;

    public AbstractAutomatonPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        this(NAME, powerType, isTurnBased, owner, source, amount, "");
    }

    public AbstractAutomatonPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, String IDModifier) {
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

        Texture normalTexture = TextureLoader.getTexture("bronzeResources/images/powers/" + NAME + "32.png");
        Texture hiDefImage = TextureLoader.getTexture("bronzeResources/images/powers/" + NAME + "84.png");
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

    public AbstractAutomatonPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean loadImage) {
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
            Texture normalTexture = TextureLoader.getTexture("bronzeResources/images/powers/" + NAME + "32.png");
            Texture hiDefImage = TextureLoader.getTexture("bronzeResources/images/powers/" + NAME + "84.png");
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

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}