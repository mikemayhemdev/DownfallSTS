package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class RapidStrikes extends AbstractChampCard {

    public final static String ID = makeID("RapidStrikes");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 3;

    public RapidStrikes() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        // tags.add(ChampMod.FINISHER);
      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        int x = AbstractDungeon.cardRandomRng.random(0, 2);
        int q = ChampMod.techniquesThisTurn + magicNumber;
        for (int i = 0; i < q; i++) {
            AbstractGameAction.AttackEffect r = null;
            switch (x) {
                case 0:
                    r = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
                    break;
                case 1:
                    r = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
                    break;
                case 2:
                    r = AbstractGameAction.AttackEffect.SLASH_VERTICAL;
                    break;
            }
            dmg(m, r);
        }
        // finisher();
    }

    public void applyPowers() {
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + (ChampMod.techniquesThisTurn + magicNumber);
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}