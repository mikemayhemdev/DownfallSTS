package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import expansioncontent.expansionContentMod;


public class LastStand extends AbstractExpansionCard {
    public final static String ID = makeID("LastStand");

    private static final int MAGIC = 10;
    private static final int UPGRADE_MAGIC = 10;

    public LastStand() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_champ.png", "expansioncontentResources/images/1024/bg_boss_champ.png");

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        //if (upgraded) this.poison = this.magicNumber +3; else {this.poison = this.magicNumber +2;}
        atb(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.LOW));

        atb(new VFXAction(p, new InflameEffect(p), 0.15F));

        atb(new RemoveDebuffsAction(p));

        atb(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        double currentPct = p.currentHealth * 1.001 / p.maxHealth * 1.001;
        if (currentPct < 0.5) {
            AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, Champ.DIALOG[6], true));

            atb(new VFXAction(p, new InflameEffect(p), 0.1F));
            atb(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
            atb(new VFXAction(p, new InflameEffect(p), 0.1F));
            if (upgraded) atb(new HealAction(p, p, this.magicNumber));
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}



