package champ.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.util.TextureLoader;

import java.util.Iterator;

public class TrophyPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("TrophyPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/CalledShot84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/CalledShot32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrophyPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard c2, UseCardAction action) {
        if (c2.canUpgrade()){

            c2.upgrade();
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.player.bottledCardUpgradeCheck(c2);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c2.makeStatEquivalentCopy()));

            AbstractCard c;

            for (AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
                c = abstractCard;
                if (c.uuid.equals(c2.uuid)) {
                    c.upgrade();
                    break;
                }
            }
        }

    }

    @Override
    public void updateDescription() {
        if (amount == 1) description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrophyPower(amount);
    }
}