package champ.cards;

import champ.ChampMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrookedStrike extends AbstractChampCard {

    public final static String ID = makeID("CrookedStrike");

    public CrookedStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.FINISHER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

        finisher();
    }

    public void upp() {
        upgradeDamage(3);
    }


}