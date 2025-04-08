package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static champ.ChampMod.loadJokeCardImage;

@CardIgnore
public class Riposte extends AbstractChampCard {

    public final static String ID = makeID("Riposte");

    public Riposte() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 0;
        isEthereal = true;
        tags.add(CardTags.STRIKE);
        exhaust = true;
        postInit();
        loadJokeCardImage(this, "Riposte.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    public void upp() {
        upgradeDamage(3);
    }
}