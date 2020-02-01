package expansioncontent.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import expansioncontent.expansionContentMod;
import theHexaghost.actions.BurnAction;


public class Hexaburn extends AbstractExpansionCard {
    public final static String ID = makeID("Hexaburn");

    private static final int DAMAGE = 6;

    public Hexaburn() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (!upgraded) atb(new LoseHPAction(p, p, 6));

        atb(new VFXAction(p, new ScreenOnFireEffect(), .2F));
        for (int i = 0; i < 6; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    addToTop(new DamageAction(m, makeInfo(), AttackEffect.FIRE));
                }
            });
        }



    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}





