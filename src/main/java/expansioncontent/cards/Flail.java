package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import expansioncontent.expansionContentMod;

public class Flail extends AbstractExpansionCard {
    public final static String ID = makeID("Flail");

    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int downfallMagic = 2;

    public Flail() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_automaton.png", "expansioncontentResources/images/1024/bg_boss_automaton.png");
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "Flail.png");
        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
        baseDownfallMagic = downfallMagic;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < downfallMagic; i++) {
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
        atb(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
         //   upgradeMagicNumber(1);
        }
    }

}
