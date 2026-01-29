package theHexaghost.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.powers.BurnPower;

public class HeatShield extends AbstractHexaCard {

    public final static String ID = makeID("HeatShield");

    public HeatShield() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 0;
        this.exhaust = true;
        HexaMod.loadJokeCardImage(this, "HeatShield.png");
    }

//    @Override
//    protected void applyPowersToBlock() {
//        if (AbstractDungeon.isPlayerInDungeon()) {
//            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
//                int realBaseBlock = this.baseBlock;
//                super.applyPowersToBlock();
//                this.magicNumber = this.baseMagicNumber = this.block;
//                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                    if (!mo.isDead && !mo.isDeadOrEscaped() &&  mo.hasPower(BurnPower.POWER_ID))
//                        baseBlock += mo.getPower(BurnPower.POWER_ID).amount;
//                }
//                super.applyPowersToBlock();
//                this.baseBlock = realBaseBlock;// 75
//                this.isBlockModified = block != baseBlock;
//                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];// 73
//                initializeDescription();
//            }
//        } else {
//            this.baseBlock = this.block = 0;
//            this.isBlockModified = false;
//        }
//    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real_baseBlock = baseBlock;
        AbstractPower po = mo.getPower(BurnPower.POWER_ID);
        if (po != null) {
            baseBlock += po.amount ;
        }
        super.calculateCardDamage(mo);
        baseBlock = real_baseBlock;
        this.isBlockModified = block != baseBlock;
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT){
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        }else{
            this.rawDescription = EXTENDED_DESCRIPTION[0] + DESCRIPTION;
        }
        initializeDescription();
    }

    public void onMoveToDiscard() {
        if (!upgraded) {
            this.rawDescription = DESCRIPTION;
        }else {
            this.rawDescription = UPGRADE_DESCRIPTION;
        }
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractPower po = m.getPower(BurnPower.POWER_ID);
                if (po != null) {
                    ((TwoAmountPower) po).amount2 += 1;
                    po.updateDescription();
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}