package automaton.cards;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.EtherealMod;

import java.util.ArrayList;
import java.util.Comparator;

public class WildBeam extends AbstractBronzeCard {

    public final static String ID = makeID("WildBeam");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 2;

    public WildBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);

        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        for (AbstractCard c:p.discardPile.group
             ) {if (c.type==CardType.STATUS){
                 cardsList.add(c);
        }

        }
        cardsList.sort(Comparator.comparing(o -> o.name));
        atb(new SelectCardsAction(cardsList, 1, EXTENDED_DESCRIPTION[0], (cards) -> {
                p.drawPile.moveToHand(cards.get(0));
                CardModifierManager.addModifier(cards.get(0), new EtherealMod());
        }));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);

        upgradeMagicNumber(1);
    }
}