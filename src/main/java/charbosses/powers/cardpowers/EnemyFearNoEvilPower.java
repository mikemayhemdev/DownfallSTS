package charbosses.powers.cardpowers;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import downfall.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnemyFearNoEvilPower extends AbstractPower {
    public static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.powers.FlameBarrierPower.class.getName());
    public static final String POWER_ID = "Fear No Evil";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public boolean isActive;
    public int theoreticalGain;
    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/WatcherFearNoEvil84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/WatcherFearNoEvil32.png"));

    public EnemyFearNoEvilPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        isActive = false;
        this.updateDescription();
//        this.loadRegion("curiosity"); //

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard) {
            return;
        }
        System.out.println("Fear no evil is active: " + isActive + "; " + this.description);
        if (card.type.equals(AbstractCard.CardType.ATTACK) && !isActive) {
            this.flash();
            isActive = true;
            updateDescription();
        }
        System.out.println("Fear no evil desc: " + this.description);
    }

    public void updateDescription() {
        if (!isActive) {
            this.description = DESCRIPTIONS[0];
        }
        else {
            this.description = DESCRIPTIONS[1];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("downfall:FearNoEvil");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
