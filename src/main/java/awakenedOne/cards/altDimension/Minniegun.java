package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import awakenedOne.cards.AbstractAwakenedCard;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class Minniegun extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Minniegun.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Minniegun() {
        super(ID, 2, CardRarity.RARE, AbstractCard.CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 2;
        baseMagicNumber = magicNumber = 5;

        frameString = "eden";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
        Wiz.atb(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}