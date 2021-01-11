package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Deprecate extends AbstractBronzeCard {

    public final static String ID = makeID("Deprecate");

    //stupid intellij stuff skill, all_enemy, common

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Deprecate() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, autoWeak(q, magicNumber));
        }
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay){
            applyToSelf(new WeakPower(AbstractDungeon.player,1, false));
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}