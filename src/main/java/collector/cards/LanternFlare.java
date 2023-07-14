package collector.cards;

import collector.powers.DoomPower;
import collector.powers.LanternFlarePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemyTop;
import static collector.util.Wiz.atb;

public class LanternFlare extends AbstractCollectorCard {
    public final static String ID = makeID(LanternFlare.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 12, 3, , , 12, 3

    public LanternFlare() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 12;
        baseSecondMagic = secondMagic = 4;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractMonster q = AbstractDungeon.getRandomMonster();
                applyToEnemyTop(q, new LanternFlarePower(q, secondMagic));
                applyToEnemyTop(q, new DoomPower(q, magicNumber));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(3);
        upgradeSecondMagic(1);
    }
}