package automaton.cards;

import basemod.BaseMod;
import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BugBarrage extends AbstractBronzeCard {

    public final static String ID = makeID("BugBarrage");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public BugBarrage() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        // exhaust = true;
        magicNumber = baseMagicNumber = 1;
    }

    private int dummyPlaceholderOf;

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Wound(), magicNumber));

        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                for (AbstractCard q : p.hand.group)
                    if (q.type == CardType.STATUS) {
                        x++;
                        att(new DamageAction(m, makeInfo(), AttackEffect.BLUNT_LIGHT));
                        att(new DiscardSpecificCardAction(q, p.hand));
                    }
                dummyPlaceholderOf = x;
            }
        });
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToTop(new DrawCardAction(dummyPlaceholderOf));
            }
        });

    }

    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
            int x = 0;
            for (AbstractCard q : AbstractDungeon.player.hand.group) if (q.type == CardType.STATUS) x++;
            int clamp = BaseMod.MAX_HAND_SIZE - (AbstractDungeon.player.hand.group.size() - 1);
            x += Math.min(magicNumber, clamp);

            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + x;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }


    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}