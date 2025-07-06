package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class SwordSigil extends AbstractChampCard {

    public final static String ID = makeID("SwordSigil");

    public SwordSigil() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
       // this.exhaust = true;
        loadJokeCardImage(this, "SwordSigil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            techique();
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.STANCE.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeMagicNumber(1);
       // upgradeCool(2);
    }

}