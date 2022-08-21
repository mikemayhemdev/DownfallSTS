package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedDodgeAndRoll;
import automaton.cards.encodedcards.EncodedPanacea;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class Flail extends AbstractBronzeCard {

    public final static String ID = makeID("Flail");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Flail() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
       // thisEncodes();
        baseAuto = auto = 2;
        exhaust = true;

        tags.add(AutomatonMod.ENCODES);
        cardsToPreview = new EncodedPanacea();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < auto; i++) {
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }

        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}