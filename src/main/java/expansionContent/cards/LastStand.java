package expansionContent.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import expansionContent.expansionContentMod;
import slimebound.cards.AbstractSlimeboundCard;


public class LastStand extends AbstractExpansionCard {
    public final static String ID = makeID("LastStand");

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public LastStand() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        //if (upgraded) this.poison = this.magicNumber +3; else {this.poison = this.magicNumber +2;}
        atb(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.LOW));

        atb(new VFXAction(p, new InflameEffect(p), 0.15F));
        atb(new RemoveDebuffsAction(p));
        double currentPct = p.currentHealth * 1.001 / p.maxHealth * 1.001;
        if (currentPct > 0.5) {
            atb(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        } else{
            AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, "~DIE~ ~.~ ~.~ ~.~", true));

            // atb(new ApplyPowerAction(p, p, new StrengthPower(p, this.poison), this.poison));

            atb(new VFXAction(p, new InflameEffect(p), 0.15F));
            atb(new VFXAction(p, new InflameEffect(p), 0.15F));
        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}



