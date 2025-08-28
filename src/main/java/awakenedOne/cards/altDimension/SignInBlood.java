package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.util.Wiz;
import awakenedOne.cards.AbstractAwakenedCard;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class SignInBlood extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SignInBlood.class.getSimpleName());

    public SignInBlood() {
        super(ID, 0, CardRarity.RARE, CardType.SKILL, CardTarget.SELF);
        baseSecondMagic = secondMagic = 3;
        baseMagicNumber = magicNumber = 3;
        tags.add(CardTags.HEALING);

        exhaust = true;
        frameString = "towertactics";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, secondMagic, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        Wiz.atb(new DrawCardAction(this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}