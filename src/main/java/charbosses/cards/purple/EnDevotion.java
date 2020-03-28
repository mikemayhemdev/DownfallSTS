package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyDevaFormPower;
import charbosses.powers.cardpowers.EnemyDevotionPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Devotion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.vfx.combat.DevotionEffect;

import java.util.ArrayList;

public class EnDevotion extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Devotion";
    private static final CardStrings cardStrings;

    public EnDevotion() {
        super(ID, cardStrings.NAME, "purple/power/devotion", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("HEAL_2", -0.4F, true));
        float doop = 0.8F;
        if (Settings.FAST_MODE) {
            doop = 0.0F;
        }

        this.addToBot(new VFXAction(new DevotionEffect(), doop));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDevotionPower(m, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new EnDevotion();
    }

    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.ENLIGHTENMENT.NAMES[0].toLowerCase());
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Devotion");
    }
}
